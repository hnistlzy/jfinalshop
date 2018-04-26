package com.demo.common;

import com.alibaba.fastjson.JSON;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class PhoneCode {
    public  static String getCode(){
        StringBuilder sb = new StringBuilder();
        char[] chars = "0123456789".toCharArray();
        Random random = new Random();
        int index =0;
        for(int i=0;i<6;i++){
            index = random.nextInt(9);
            sb.append(chars[index]);
        }
        return sb.toString();
    }
    public static String getJson() {
        String result =null;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", PhoneCode.getCode());
        result= JSON.toJSONString(map);
        return  result;
    }
    public static  void main(String args[]){
        System.out.println(PhoneCode.getJson());
    }

}