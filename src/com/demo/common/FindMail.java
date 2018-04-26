package com.demo.common;

public class FindMail {
    public  String getMail(String mail){
        int left=0;
        int right=0;
        char[] chars = mail.toCharArray();
        for (int i = 0; i <chars.length ; i++) {
            if(chars[i] =='@'){
                left=i;
            }
            if (chars[i]=='.'&& chars[i+1]=='c'){
                right=i;
            }
        }
        return mail.substring(left+1,right);

    }

//    public static void main(String[] args) {
//        FindMail findMail = new FindMail();
//        String mail = findMail.getMail("952737510@qq.com");
//        System.out.println(mail);
//    }
}
