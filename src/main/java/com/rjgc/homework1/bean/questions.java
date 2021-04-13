package com.rjgc.homework1.bean;

import lombok.Data;

import java.util.List;
@Data
public class questions {
    private List<question> questions;
    public questions(List questions){
        this.questions=questions;
    }
}
