package com.example.dzs_demo.utils;

import com.alibaba.fastjson2.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Result {
    public static String okGetString(){
        Map<String,Object> map=new HashMap<>();
        map.put("code",200);
        map.put("message","ok");
        String r= JSONObject.toJSONString(map);
        return r;
    }

    public static String error(Integer code, String msg) {
        Map<String,Object> map=new HashMap<>();
        map.put("code",code);
        map.put("message",msg);
        String r= JSONObject.toJSONString(map);
        return r;

    }

    public static String data(Object data){
        Map<String,Object> map=new HashMap<>();
        map.put("code",200);
        map.put("message","ok");
        map.put("data",data);
        String r= JSONObject.toJSONString(map);
        return r;
    }
}
