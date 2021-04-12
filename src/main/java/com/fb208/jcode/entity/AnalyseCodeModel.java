package com.fb208.jcode.entity;

import com.fb208.jcode.annotation.Analyse;
import lombok.Data;

@Data
public class AnalyseCodeModel {

    @Analyse(len = 2)
    String head;
    @Analyse(len=6)
    String body;
    @Analyse(len=2)
    String foot;
}
