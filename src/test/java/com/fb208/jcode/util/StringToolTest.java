package com.fb208.jcode.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringToolTest {

    @Test
    public void trimStartTest()
    {
        System.out.println(StringTool.trimStart("aaa123456a","aaa"));
    }

    @Test
    public void clearCharTest()
    {
        System.out.println(StringTool.clearChar("(11)211127(17)261126(10)CD10081(21)0001","(",")"));
    }
}