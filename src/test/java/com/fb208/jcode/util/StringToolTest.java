package com.fb208.jcode.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringToolTest {

    @Test
    public void trimStartTest()
    {
        System.out.println(StringTool.trimStart("aaa123456a","aaa"));
    }

}