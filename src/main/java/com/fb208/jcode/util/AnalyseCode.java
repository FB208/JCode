package com.fb208.jcode.util;

import com.fb208.jcode.SpringbootMain;
import com.fb208.jcode.annotation.Analyse;
import com.fb208.jcode.entity.AnalyseCodeModel;
import org.apache.ibatis.javassist.runtime.Desc;
import org.omg.DynamicAny.NameValuePair;
import org.springframework.boot.SpringApplication;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class AnalyseCode {
    public static void main(String[] args) {
        AnalyseCode a = new AnalyseCode();
        AnalyseCodeModel codeModel = a.parseModel();
        int aa=1;
    }

    public AnalyseCodeModel parseModel() {
        AnalyseCodeModel codeModel = new AnalyseCodeModel();
        try {
            String str = "AA123456BB";
            Field[] fields = codeModel.getClass().getDeclaredFields();
            if (fields == null || fields.length == 0) {
                return null;
            }
            for (Field f : fields) {
                if (f.getName() != null) {
                    Analyse analyse = f.getAnnotation(Analyse.class);
                    int len = analyse.len();
                    f.setAccessible(true);
                    f.set(codeModel, str.substring(0, len));
                    str = str.substring(len, str.length());
                }
            }
        } catch (Exception e)
        {
            int a =1;
        }

        return codeModel;
    }


}
