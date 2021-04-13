package com.rjgc.homework1.bean;

import lombok.Data;

import java.util.List;

@Data
public class answer {
        private String answer;
        private  List<String> process;
        public answer(String answer, List<String> process){
            this.answer=answer;
            this.process=process;
        }
}
