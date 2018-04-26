package com.demo.common.model;

import com.jfinal.plugin.activerecord.Model;

public class User  extends Model<User> {
    int uid;
    String phone;
    String password;
    int age;
    String email;
    String activationCode;
    int isActivated; // 0:未激活，1:已激活
    String nickname;
    String realname;

}
