package com.rjgc.homework1.bean;

import lombok.Data;

import java.util.List;

@Data
public class question {
    private String question;
    private String[] answers;
    private int correctAnswer;
    private List<String> process;
    public question( String ques,String[] answer,int trueans,List<String> process){
        this.question=ques;
        this.answers=answer;
        this.correctAnswer=trueans;
        this.process=process;
    }
    public void Setprocess(List<String>  process){
        this.process=process;
    }
}
