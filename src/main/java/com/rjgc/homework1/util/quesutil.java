package com.rjgc.homework1.util;

import com.rjgc.homework1.bean.answer;
import com.rjgc.homework1.bean.question;

import java.io.*;
import java.util.*;

public class quesutil {
    private static boolean sdfen=false;
    private  Map<String, Set> map=new HashMap<>();
     public static void main(String []args){

    }
    public  boolean duplicate(question qs){
         String answer=qs.getAnswers()[qs.getCorrectAnswer()-1];
         int flag=1;
         if(map.containsKey(answer)){
                Set set=map.get(answer);
                     Iterator<question> it = set.iterator();
                     while (it.hasNext()) {
                         question qstemp = it.next();
                         qstemp.getProcess();
                         int i=0;
                         if(qstemp.getQuestion()==qs.getQuestion()){
                             System.out.println(qstemp.getQuestion());
                             return false;
                         }else{
                             String qs1=qstemp.getQuestion();
                             String qs2=qs.getQuestion();
                             char[] qs1char=qs1.toCharArray();
                             char[] qs2char=qs2.toCharArray();
                             Comparator<Integer> OrderIsdn =  new Comparator<Integer>(){
                                 public int compare(Integer o1, Integer o2) {
                                     // TODO Auto-generated method stub
                                     int numbera = o1;
                                     int numberb = o2;
                                     if(numberb > numbera)
                                     {
                                         return 1;
                                     }
                                     else if(numberb<numbera)
                                     {
                                         return -1;
                                     }
                                     else
                                     {
                                         return 0;
                                     }

                                 }



                             };
                             Queue<Integer> priorityQueue =  new PriorityQueue<Integer>(11,OrderIsdn);
                             Queue<Integer> priorityQueue2 =  new PriorityQueue<Integer>(11,OrderIsdn);


                            for(int j=0;j<qs1char.length;j++){
                               if(Character.isDigit(qs1char[j])){
                                   int temp = Integer.parseInt(qs1char[j] + "");
                                   while(j+1<qs1char.length&&Character.isDigit(qs1char[j+1])){
                                            temp=temp*10+Integer.parseInt(qs1char[j+1] + "");
                                            j++;
                                   }

                                        priorityQueue.add(temp);

                                }
                            }
                             for(int j=0;j<qs2char.length;j++){
                                 if(Character.isDigit(qs2char[j])) {
                                     int temp = Integer.parseInt(qs2char[j] + "");
                                     while (j + 1 < qs2char.length && Character.isDigit(qs2char[j + 1])) {
                                         temp = temp * 10 + Integer.parseInt(qs2char[j + 1] + "");
                                         j++;
                                     }
                                     priorityQueue2.add(temp);
                                 }


                             }
                             while (!priorityQueue.isEmpty()&&!priorityQueue2.isEmpty()){
                                    if(priorityQueue.poll()!=priorityQueue2.poll()){
                                        Set tempset=new HashSet(set);
                                        tempset.add(qs);
                                        map.put(qs.getAnswers()[qs.getCorrectAnswer()-1], tempset);
                                     //   System.out.println(qstemp.getQuestion());
                                        return true;
                                    }

                             }
                             if(priorityQueue.isEmpty()&&priorityQueue2.isEmpty()){
                                System.out.println(qs.getQuestion());
                                System.out.println(qstemp.getQuestion());
                                 return false;
                             }


                         }
//                         while(i<qstemp.getProcess().size()-1&&i<qs.getProcess().size()-1){
//                            if(qstemp.getProcess().get(i)==qs.getProcess().get(i)){
//                            System.out.println(qstemp.getQuestion());
//                                return false;
//                            }
//                         }

                     }

             set.add(qs);
             map.put(qs.getAnswers()[qs.getCorrectAnswer()-1], set);

         }else {
             Set<question> newset = new HashSet<>();
             newset.add(qs);
             map.put(qs.getAnswers()[qs.getCorrectAnswer()-1], newset);
         }


      // System.out.println("============================");
         return true;
    }
    public static String Blank(String s) {
        return s.replaceAll("\\s+", "");
    }
    public  void write(List<question> data) {
         int count=1;
         System.out.println(data.size());
        File file = new File(this.getClass().getResource("/test.doc").getPath());
        System.out.println(file.getPath());
        try {
            file.createNewFile(); // 创建文件
            FileOutputStream in = new FileOutputStream(file);
            for(question qs:data) {
                String str = count+"."+qs.getQuestion()+"= ?" + "\r\n";
                str+="A."+qs.getAnswers()[0]+"\r\n";
                str+="B."+qs.getAnswers()[1]+"\r\n";
                str+="C."+qs.getAnswers()[2]+"\r\n";
                str+="D."+qs.getAnswers()[3]+"\r\n";
                byte bt[] = new byte[1024];
                count++;
                bt = str.getBytes();
                in.write(bt, 0, bt.length);
            }
            in.close();
                    } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {

        }


         //向文件写入内容(输出流)



        try {
            // 读取文件内容 (输入流)
            FileInputStream out = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(out);
            int ch = 0;
            while ((ch = isr.read()) != -1) {
                System.out.print((char) ch);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
    public static List<String> toMid(String s) {
        s = Blank(s);
        List<String> list = new ArrayList<String>();
        String temp = "";
        for (int i = 0; i < s.length(); i++) {
//			System.out.println("charAt:"+s.charAt(i));
            if (isSymble(s.charAt(i))) {
                list.add(s.charAt(i) + "");
            } else if (s.charAt(i) >= '0' && s.charAt(i) <= '9' || s.charAt(i) == '.') {
                temp = temp + s.charAt(i);
//				System.out.println(temp);
                if (i == s.length() - 1) {
                    list.add(temp);
                } else if (isSymble(s.charAt(i + 1))) {
                    list.add(temp);
                    temp = "";
                }
            }
        }
        return list;
    }
    public static List<String> toPoland(List<String> s) {
        List<String> list = new ArrayList<String>();
        Stack<String> oper = new Stack<String>();
        for (String string : s) {

            if (isNum(string)) {
                list.add(string);
            } else if (string.equals("(")) {
                oper.push(string);
            } else if (string.equals(")")) {

                while (!oper.peek().equals("(")) {
                    list.add(oper.pop());
                }
                oper.pop();
            } else {
                while (oper.size()!=0 && priority(string) <= priority(oper.peek())) {
                    list.add(oper.pop());
                }
                oper.push(string);
            }
//			System.out.println("oper="+oper);
        }
        while (!oper.empty()) {
            list.add(oper.pop());
        }
        return list;
    }
    public static Boolean isSymble(char c) {
        return c == '+' || c == '-' || c == 'x' || c == '/' || c == '(' || c == ')'||c=='^'||c=='*';
    }
    public static Boolean isNum(String s) {
        return s.matches("^\\d+.?\\d*$");
    }
    public static int priority(String c) {
        if (c.equals("+") || c.equals("-")) {
            return 1;
        }
        if(c.equals("x")||c.equals("/")) {
            return 2;
        }
        if(c.equals("^")){
            return 3;
        }
        return 0;
    }
    public static answer calculatefen(List<String> s,String ques) {
        String res="";
        String num1="";
        String num2="";
        Stack<String> s1=new Stack<String>();
        int[] resall=new int[2];
        List<String> process=new ArrayList<>();
        for (String item : s) {
//			System.out.println("s1="+s1);
            if(isNum(item)) {
                s1.push(item);
            }else {
                num1=s1.pop();
                num2=s1.pop();
                if (item.equals("+")) {
                    if(!num1.contains("/")&&!num2.contains("/")) {
                        res = (Integer.parseInt(num1) + Integer.parseInt(num2))+"";
                    }else{
                        res=calculatejia(num1,num2);
                    }
                    ques = ques.replace("(" + num2 + "+" + num1 + ")", "" + res);
                    ques = ques.replace(num2 + "+" + num1, "" + res);
                    process.add(ques);
                } else if (item.equals("-")) {
                    if(!num1.contains("/")&&!num2.contains("/")) {

                        res = (Integer.parseInt(num2) -Integer.parseInt(num1))+"";
                    }else{
                        res=calculatejian(num1,num2);
                    }
                    ques = ques.replace("(" + num2 + "-" + num1 + ")", "" + res);
                    ques = ques.replace(num2 + "-" + num1, "" + res);
                    process.add(ques);
                } else if (item.equals("x")) {
                    if(!num1.contains("/")&&!num2.contains("/")) {
                        res = (Integer.parseInt(num1) * Integer.parseInt(num2)) + "";

                    }else{
                        res=calculatechen(num1,num2);
                    }
                    ques = ques.replace("(" + num2 + "x" + num1 + ")", "" + res);
                    ques = ques.replace(num2 + "x" + num1, "" + res);
                    process.add(ques);
                } else if (item.equals("/")) {
                    if(!num1.contains("/")&&!num2.contains("/")) {
                        if (sdfen&&Double.parseDouble(num2) % Double.parseDouble(num1) != 0) {
                            res = num2 + "/" + num1;
                        }else if(Double.parseDouble(num2) % Double.parseDouble(num1) != 0){
                            return new answer("-1",null);
                        } else {
                            res = (Integer.parseInt(num2) /Integer.parseInt(num1)) + "";
                        }
                    }else{
                        res=calculatechu(num1,num2);
                    }
                    ques=ques.replace("("+num2+"/"+num1+")", ""+res);
                    ques=ques.replace(num2+"/"+num1, ""+res);
                    process.add(ques);

                } else if (item.equals("^")) {
                    if(num1.contains("/")||num2.contains("/")||Integer.parseInt(num1)<0||Integer.parseInt(num1)>4){
                        return new answer("-1",null);
                    }
                    int num3=Integer.parseInt(num1);
                    res=num2;
                    int tmpt=Integer.parseInt(num2);
                    while(num3>1){
                        tmpt*=(Integer.parseInt(num2));
                        num3--;
                    }
                    res=tmpt+"";
               //     System.out.println(num2+"^"+num1);
                    ques=ques.replace("("+num2+"^"+num1+")", ""+res);
                    ques=ques.replace(num2+"^"+num1, ""+res);
                    process.add(ques);

                } else {
                    throw new RuntimeException("运算符有误");
                }
                s1.push(""+res);

            }
        }

        return new answer(s1.pop(),process);
    }
    private static String getgcd(String a){
         int m=Integer.parseInt(a.substring(0,a.indexOf("/")));
         int n=Integer.parseInt(a.substring(a.indexOf("/")+1));
        int fan = 0,x=m,y=n;
        if(m<n) {              //始终保持m比n大，
            fan=m;m=n;n=fan;
        }
        while(m%n!=0) {        //运用辗转相除法求出最大公约数
            fan=m%n;
            m=n;
            n=fan;
        }
        if(y==n){return x/n+"";}
        return x/n+"/"+y/n;
    }
    private static int gcd(int m,int n){
        int fan = 0,x=m,y=n;
        if(m<n) {              //始终保持m比n大，
            fan=m;m=n;n=fan;
        }
        while(m%n!=0) {        //运用辗转相除法求出最大公约数
            fan=m%n;
            m=n;
            n=fan;
        }

        return x*y/n;
    }
    private static String calculatejian(String num1, String num2) {
        String res="";
        int a=0;
        if(!num2.contains("/")){
            num2=num2+"/1";
        }
        if(!num1.contains("/")){
            num1=num1+"/1";
        }
        if(num1.contains("/")&&num2.contains("/")){
            int num1s=Integer.parseInt(num1.substring(0,num1.indexOf("/")));
            int num1x=Integer.parseInt(num1.substring(num1.indexOf("/")+1));
            int num2s=Integer.parseInt(num2.substring(0,num2.indexOf("/")));
            int num2x=Integer.parseInt(num2.substring(num2.indexOf("/")+1));
            a=gcd(num1x,num2x);
            res=(a/num2x*num2s-a/num1x*num1s)+"/"+a;
            res=getgcd(res);

        }

        System.out.println(res);
        return res;
    }
    private static String calculatejia(String num1, String num2) {
        String res="";
        int a=0;
        if(!num2.contains("/")){
            num2=num2+"/1";
        }
        if(!num1.contains("/")){
            num1=num1+"/1";
        }
        if(num1.contains("/")&&num2.contains("/")){
            int num1s=Integer.parseInt(num1.substring(0,num1.indexOf("/")));
            int num1x=Integer.parseInt(num1.substring(num1.indexOf("/")+1));
            int num2s=Integer.parseInt(num2.substring(0,num2.indexOf("/")));
            int num2x=Integer.parseInt(num2.substring(num2.indexOf("/")+1));
            a=gcd(num1x,num2x);
            res=(a/num1x*num1s+a/num2x*num2s)+"/"+a;
            res=getgcd(res);

        }

        System.out.println(res);
        return res;
    }
    private static String calculatechu(String num1, String num2) {
        String res="";
        int a=0;
        if(!num2.contains("/")){
            num2=num2+"/1";
        }
        if(!num1.contains("/")){
            num1=num1+"/1";
        }
        if(num1.contains("/")&&num2.contains("/")){
            int num1s=Integer.parseInt(num1.substring(0,num1.indexOf("/")));
            int num1x=Integer.parseInt(num1.substring(num1.indexOf("/")+1));
            int num2s=Integer.parseInt(num2.substring(0,num2.indexOf("/")));
            int num2x=Integer.parseInt(num2.substring(num2.indexOf("/")+1));

            res=(num1x*num2s)+"/"+(num1s*num2x);
            System.out.println(res);
            res=getgcd(res);

        }
        System.out.println(res);
        return res;
    }
    private static String calculatechen(String num1, String num2) {
         String res="";
         int a=0;
        if(!num2.contains("/")){
            num2=num2+"/1";
        }
        if(!num1.contains("/")){
            num1=num1+"/1";
        }
        if(num1.contains("/")&&num2.contains("/")){
            int num1s=Integer.parseInt(num1.substring(0,num1.indexOf("/")));
            int num1x=Integer.parseInt(num1.substring(num1.indexOf("/")+1));
            int num2s=Integer.parseInt(num2.substring(0,num2.indexOf("/")));
            int num2x=Integer.parseInt(num2.substring(num2.indexOf("/")+1));

            res=(num1s*num2s)+"/"+(num1x*num2x);
            System.out.println(res);
            res=getgcd(res);

        }
        System.out.println(res);
         return res;
    }
    public static answer calculate(List<String> s,String ques) {
        double res=0;
        String stres="";
        double num1=0;
        double num2=0;
        Stack<String> s1=new Stack<String>();
        List<String> process=new ArrayList<>();
        for (String item : s) {
//			System.out.println("s1="+s1);
            if(isNum(item)) {
                s1.push(item);
            }else {
                num1=Double.parseDouble(s1.pop());
                num2=Double.parseDouble(s1.pop());
                if (item.equals("+")) {
                    res = num1 + num2;
                    ques=ques.replace("("+(int)num2+"+"+(int)num1+")", ""+(int)res);
                    ques=ques.replace((int)num2+"+"+(int)num1, ""+(int)res);
                    process.add(ques);

                } else if (item.equals("-")) {
                    res = num2 - num1;
                    ques=ques.replace("("+(int)num2+"-"+(int)num1+")", ""+(int)res);
                    ques=ques.replace((int)num2+"-"+(int)num1, ""+(int)res);
                    process.add(ques);

                } else if (item.equals("x")) {
                    res = num1 * num2;
                    ques=ques.replace("("+(int)num2+"x"+(int)num1+")", ""+(int)res);
                    ques=ques.replace((int)num2+"x"+(int)num1, ""+(int)res);
                    process.add(ques);

                } else if (item.equals("/")) {
                    if(num2%num1!=0){
                        return new answer("-1",null);
                     //   stres=num2+"/"+num1;
                    }
                    res = num2 / num1;
                    ques=ques.replace("("+(int)num2+"/"+(int)num1+")", ""+(int)res);
                    ques=ques.replace((int)num2+"/"+(int)num1, ""+(int)res);
                    process.add(ques);

                } else if (item.equals("^")) {
                    if(num1<0){
                        return new answer("-1",null);
                    }
                    double num3=num1;
                    res=num2;
                    while(num3>1){
                        res*=num2;
                        num3                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      --;
                    }
                    System.out.println((int)num2+"^"+(int)num1);
                    ques=ques.replace("("+(int)num2+"^"+(int)num1+")", ""+(int)res);
                    ques=ques.replace((int)num2+"^"+(int)num1, ""+(int)res);
                    process.add(ques);

                } else {
                    throw new RuntimeException("运算符有误");
                }
                s1.push(""+res);

            }
        }

        return new answer(s1.pop(),process);
    }
    public List getequation(int total) {
        String simple[] = {"+", "-", "x", "/","^"};
        int count=0;
        List<question> result=new ArrayList<>();
        List<question> suanshi=new ArrayList<>();

        while(count<total){
            suanshi=getques(1,2);
            String ques=suanshi.get(0).getQuestion();
            String[] answers=suanshi.get(0).getAnswers();
            String answer=answers[suanshi.get(0).getCorrectAnswer()-1];
            System.out.println(ques);
            System.out.println(answer);
            count++;
            int len=ques.length();
            char[] charstr=ques.toCharArray();
            for(int i=0;i<len;i++){
                if(isNum(charstr[i]+"")&&Math.random()*6>3){
                    String temp="";
                    int y=i;
                    while(i>0&&isNum(charstr[i-1]+"")) {
                        temp+=charstr[i-1]+"";
                        i--;
                    }
               temp+=charstr[i]+"";
                    while(y<len-1&&isNum(charstr[y+1]+"")) {
                        temp+=charstr[y+1]+"";
                        y++;
                    }
                    System.out.println("替换"+temp);
                    ques = ques.replace(temp,"Y");
                    //    System.out.println("随机后" + str);
                    ques+="="+ answers[suanshi.get(0).getCorrectAnswer()-1];
                    ques+=",则Y等于多少？";
                    answers[suanshi.get(0).getCorrectAnswer()-1]=temp;

                    break;
                }
            }
            if(!ques.contains("Y")){
                     int i=0;
                    String temp=charstr[i]+"";
                    while (isNum(charstr[i+1]+"")) {
                        temp+=charstr[i+1]+"";
                        i++;

                    }
                ques = ques.replace(temp,"Y");
                ques+="="+ answers[suanshi.get(0).getCorrectAnswer()-1];
                //    System.out.println("随机后" + str);
                ques+=",则Y等于多少？";
                answers[suanshi.get(0).getCorrectAnswer()-1]=temp;
            }

            System.out.println(new question(ques,answers,suanshi.get(0).getCorrectAnswer(),suanshi.get(0).getProcess()));
            result.add(new question(ques,answers,suanshi.get(0).getCorrectAnswer(),suanshi.get(0).getProcess()));
        }


        return result;
    }
    public List getques(int total,int level) {
         /*
         * 参数控制难度*/
        int sdresult=500;
        int sdkuohao=0;
        int sdzifang=0;
        int sdpar=0;
        int sdchenchu=1;
         switch (level){
             case 1:
                 //小学一年级难度。只有乘除没有括号，没有次方，结果在100以下,运算符为1到2位
                 sdresult=10;
                 sdkuohao=0;
                 sdchenchu=0;
                 sdzifang=0;
                 sdpar=1;
                break;
             case 2:
                 //小学二年级难度。没有括号，没有次方，结果在600以下,运算符为2到3位
                 sdresult=600;
                 sdkuohao=0;
                 sdzifang=0;
                 sdpar=2;
                 break;
             case 3:
                 //小学三年级难度。有括号，没有次方，结果在800以下,运算符为2到3位
                 sdresult=800;
                 sdkuohao=1;
                 sdzifang=0;
                 sdpar=3;
                 break;
             case 4:
                 //小学四年级难度。有括号，有次方，结果在1000以下,运算符为3到4位
                 sdresult=1000;
                 sdkuohao=1;
                 sdzifang=1;
                 sdpar=4;
                 break;
             case 5:
                 sdresult=1000;
                 sdkuohao=1;
                 sdzifang=1;
                 sdpar=4;
                 sdfen=true;
                 break;
             default:
                 sdresult=1000;
                 sdkuohao=0;
                 sdzifang=0;
                 sdpar=0;
                 break;
         }
        String simple[] = {"+", "-", "x", "/","^"};
        int count=0;
        List<question> result=new ArrayList<>();
        int pre=0;

        while(count<total){
            //随机生成【1-100】之间的随机数
            String str = String.valueOf((int) (Math.random() * 100 + 1));
            pre=Integer.parseInt(str);
            //生成四则运算的数字个数,至少三个,最多五个
            int a = (int) (Math.random() +sdpar+1);
            //运算符位置
            int p=4;
            if(sdchenchu==0){
                p=2;
            }
            if(sdzifang==1) {//可以有次方
                 p = 5;
            }
            for (int i = 0; i < a - 1; i++) {
                //生成四则运算的数字
                int b = (int) (Math.random() * 100 + 1);
                //生成运算符的位置
                int c = (int) (Math.random() * p);
                if(c==4){
                    b=(int)(Math.random()*3+1);
                    p=4;
                }
                if (!sdfen&&c == 3) {
                    int ac = 5;
                    while (ac != 0 && pre % ac != 0) {
                        ac += Math.random() * 2 - 2;
                    }
                    if (ac != 0 && ac != 1) {
                        b = ac;
                    } else {
                        c = 2;
                    }
                }
                pre = b;
                str += simple[c] + String.valueOf(b);
            }

            if(sdkuohao==1&&Math.random()*4>2){
                int len =str.length();
                char[] charstr=str.toCharArray();
                int right=len+1;
                for(int i=1;i<len;i++){
                    if(isNum(charstr[i]+"")&&Math.random()*4>2){
                        while(i>=1&&isNum(charstr[i-1]+"")) {
                                 i--;
                        }
                        str = str.substring(0, i) + "(" + str.substring(i);
                 //    System.out.println("随机后" + str);
                        right = i ;
                        break;
                    }
                }

                int countsym=0;
                for(int i=right;i<len;i++){
                    if(isSymble(charstr[i])) {
                        if(countsym>Math.random()*2+1) {

                            str = str.substring(0, i + 1) + ")" + str.substring(i + 1);
                            break;
                        }
                        countsym++;
                    }
                }
                //System.out.println("2随机后" + str);
                if(str.contains("(")&&!str.contains(")")){
                    str=str+")";
                }

            }

            answer answerall=calculatefen(toPoland(toMid(str)),str);
            String answer=answerall.getAnswer();
            if(!answer.contains("/")) {
                answer = (int) Double.parseDouble(answer) + "";
            }
        //    System.out.println(answer);
            if(!answer.contains("-")&&!answer.contains("/")&&Integer.parseInt(answer)<sdresult
           ){

                int trueans = (int) (Math.random() * 4);
                String[] ansarr=new String[4];
                ansarr[trueans]=answer;
                for(int i=0;i<4;i++){
                    if(i!=trueans){
                        ansarr[i]= (int)(Math.random() * 100 )+"";
                        while(ansarr[i]==answer){
                            ansarr[i]=(int) (Math.random() * 100 )+"";
                        }
                    }
                }
               // System.out.println(new question(str,ansarr,trueans+1,answerall.getProcess()));

                question qstemp= new question(str,ansarr,trueans+1,answerall.getProcess());
                if(duplicate(qstemp)) {
                    count++;
                    result.add(qstemp);
                }
            }else if(answer.contains("/")){
                int temtresult=Math.abs(Integer.parseInt(answer.substring(0,answer.indexOf("/"))));
                int temtresult2=Math.abs(Integer.parseInt(answer.substring(answer.indexOf("/")+1)));
                if(temtresult<100&&temtresult2<100&&temtresult>-10){

                    int trueans = (int) (Math.random() * 4);
                    String[] ansarr=new String[4];
                    ansarr[trueans]=answer;
                    for(int i=0;i<4;i++){
                        if(i!=trueans){
                            ansarr[i]= (int)(Math.random() * 100 )+"";
                            while(ansarr[i]==answer){
                                ansarr[i]=(int) (Math.random() * 100 )+"";
                            }
                        }
                    }

                //    System.out.println(new question(str,ansarr,trueans+1,answerall.getProcess()));
                    question qstemp= new question(str,ansarr,trueans+1,answerall.getProcess());
                    if(duplicate(qstemp)) {
                        count++;
                        result.add(qstemp);
                    }
                }
            }
        }
        return result;
    }
    public  int calculate(String s) {

        Stack<Integer> stack = new Stack<>();
        char opt = '+';
        int num = 0;

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (Character.isDigit(ch))
                num = num * 10 + (ch - '0');

            if ((!Character.isDigit(ch) && ch != ' ') || i == s.length() - 1) {
                if (opt == '+')
                    stack.push(num);
                else if (opt == '-')
                    stack.push(-num);
                else if (opt == '*')
                    stack.push(stack.pop() * num);
                else {
                    if(stack.peek()%num!=0){
                        return -1;
                    }
                    stack.push(stack.pop() / num);
                }
                num = 0;
                opt = ch;
            }
        }

        int res = 0;
        while (!stack.isEmpty())
            res += stack.pop();

        return res;
    }

}
