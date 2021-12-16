package com.fb208.jcode;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
public class ByteCode {
    public static void main(String[] args) throws Exception {
        ClassPool pool = ClassPool.getDefault();
        CtClass ctClass = pool.get("com.thtf.eas.common.utils.MacAddressUtil");
        CtMethod cm = ctClass.getDeclaredMethod( "validateRegistrationCode");
        cm.setBody("return true;");
        ctClass.writeFile("D:\\test");
    }
}
