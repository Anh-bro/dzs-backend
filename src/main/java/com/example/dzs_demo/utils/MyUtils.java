package com.example.dzs_demo.utils;

import com.alibaba.fastjson2.JSON;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class MyUtils {
    public static String getRandomString(){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<10;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    public static boolean ObjectToJson(Object treeNode,String filename) throws IOException {
        boolean successFlag = true;
        String jsonStr = JSON.toJSONString(treeNode);
        System.out.println(jsonStr);
        // 处理下字符串，每隔150个字符加个\n，写到文件中是才不会一行，否则太难看
        StringBuilder stringBuilder = new StringBuilder(jsonStr);
        for (int i = 150; i < stringBuilder.length(); i += 150) {
            stringBuilder.insert(i, "\n");
        }
        jsonStr = stringBuilder.toString();
        System.out.println(jsonStr);
        // 将生成的Json文件一并放入到trainingContentDir下面，到时候需要一起压缩打包成zip
        String jsonFIlePath = System.getProperty("user.dir")+"/static/JSONData/"+filename;
        try {
            // 将json字符串写入文件
            File jsonFIle = new File(jsonFIlePath);
            if (!jsonFIle.getParentFile().exists()) {
                jsonFIle.getParentFile().mkdirs();
            }
            if (jsonFIle.exists()) {
                // 文件已经存在，把旧文件删除
                jsonFIle.delete();
                jsonFIle.createNewFile();
            }
            Writer writer = new OutputStreamWriter(new FileOutputStream(jsonFIle), "UTF-8");
            writer.write(jsonStr);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            successFlag = false;
        }
        return successFlag;
    }

    public static String jsonFIleToJsonString(String jsonFilePath) throws IOException {
        String jsonStr="";
        try {
            File jsonFile = new File(jsonFilePath);
            FileReader fileReader = new FileReader(jsonFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuilder sb = new StringBuilder();
            String s = "";
            while ((s = bufferedReader.readLine()) != null) {
                s = s.replace("\n", "");
                sb.append(s);
                System.out.println(s);
            }
            bufferedReader.close();
            jsonStr = sb.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return jsonStr;
    }

}
