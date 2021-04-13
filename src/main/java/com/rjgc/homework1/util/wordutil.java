package com.rjgc.homework1.util;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.File;
import java.util.Map;

public class wordutil {
        public static void main(String []args){
            add(19,1);
        }


        public static int add(int a,int b){
            System.out.println(a^b);
            return a^b;
        }
}
