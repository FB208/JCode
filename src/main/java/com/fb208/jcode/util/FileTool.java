package com.fb208.jcode.util;

import java.io.*;

public class FileTool {
    /**
     * 创建文件夹
     * @param path
     */
    public static void createDir(String path){
        File dir=new File(path);
        if(!dir.exists())
            dir.mkdirs();
    }

    /**
     * 创建新文件
     * @param path
     * @param filename
     * @throws IOException
     */
    public static void createFile(String path,String filename) throws IOException{
        File file=new File(path+"/"+filename);
        if(!file.exists())
            file.createNewFile();
    }

    /**
     * 读文件
     * @param path
     * @return
     * @throws IOException
     */
    public static String FileInputStreamDemo(String path) throws IOException{
        File file=new File(path);
        if(!file.exists()||file.isDirectory())
            throw new FileNotFoundException();
        FileInputStream fis=new FileInputStream(file);
        byte[] buf = new byte[1024];
        StringBuffer sb=new StringBuffer();
        while((fis.read(buf))!=-1){
            sb.append(new String(buf));
            buf=new byte[1024];//重新生成，避免和上次读取的数据重复
        }
        return sb.toString();
    }

    /**
     * 读文件2
     * @param path
     * @return
     * @throws IOException
     */
    public static String BufferedReaderDemo(String path) throws IOException{
        File file=new File(path);
        if(!file.exists()||file.isDirectory())
            throw new FileNotFoundException();
        BufferedReader br=new BufferedReader(new FileReader(file));
        String temp=null;
        StringBuffer sb=new StringBuffer();
        temp=br.readLine();
        while(temp!=null){
            sb.append(temp+" ");
            temp=br.readLine();
        }
        return sb.toString();
    }

    /**
     * 写文件，可设置编码
     * 如不存在自动创建
     * @throws IOException
     */
    public static void StringBufferWrite(String filePath,String content) throws IOException{
        File file=new File(filePath);
        if(!file.exists())
            file.createNewFile();
        FileOutputStream out=new FileOutputStream(file,false);
        out.write(content.getBytes("utf-8"));
        out.close();
    }
}
