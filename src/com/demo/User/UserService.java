package com.demo.User;


import com.demo.common.model.User;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfplugin.mail.MailKit;

import java.util.UUID;

public class UserService {
    public static  User dao  =new User().dao();

    /**
     * 手机号是否已经被注册的ajax校验
     * @param phone 手机号
     * @return true=>该手机已经注册，false=>未注册
     */
    public Boolean ajaxValidatePhoneNum(String phone) {
        boolean bool=false;
        String sql = "select count(*) from t_user where phone = ?";
        Integer integer = Db.queryInt(sql, phone);
        if(integer.intValue() >0){
            //集合大于0，手机号已经被注册
            bool=true;
        }
        return bool;
    }

    /**
     * mail是否已经被注册的ajax校验
     * @param email mail
     * @return true=>该mail已经注册，false=>未注册
     */
    public Boolean ajaxValidateEmail(String email) {
        boolean bool =false;
        String sql ="select count(*) from t_user where email = ?";
        Integer integer = Db.queryInt(sql, email);
        if (integer.intValue()>0){
            bool=true;
        }
        return  bool;
    }

    /**
     * 发邮件的方法
     * @param user user对象，包含邮箱信息
     */
    public void sendEmail(User user) {
       StringBuilder sb= new StringBuilder();
      String str=sb.append("http://localhost/user/activate/").append(user.getStr("activationCode")).toString();
       MailKit.send(user.getStr("email"),null,"李朝宇毕邮箱注册",str);
    }

    /**
     * 邮箱注册
     * @param user 对象
     * @return bool
     */
    public Boolean regByEmail(User user) {
        String str = UUID.randomUUID().toString();
        str.replaceAll("-","");
        user.set("activationCode",str.replaceAll("-",""));
        user.set("isActivated",0);
        boolean bool = user.save();
        if(bool){
            sendEmail(user);
        }
        return bool;
    }

    /**
     * 根据激活码查询激活状态，没有激活则激活
     * @param str 激活码
     * @return bool
     */
    public boolean queryByActvationCode(String str) {
        boolean bool=false;
        String sql="select * from t_user where activationCode = ?";
        User user = dao.findFirst(sql, str);
        if(user.getInt("isActivated")== 0){
            //如果没有激活过=》激活成功
            user.set("isActivated",1).update();
           bool=true;
        }
        return  bool; //激活失败，已经被激活了。
    }

    /**
     * 根据激活码，查询User对象，用户邮箱注册后为用户自动登录
     * @param str 激活码
     * @return user对象
     */
    public User queryUserByActvationCode(String str){
        String sql="select * from t_user where activationCode = ?";
        return dao.findFirst(sql, str);
    }

    public User loginByEmail(User user,String loginName) {

        String sql = "select * from t_user where email = ? and password = ?";
        User model = dao.findFirst(sql, loginName, user.getStr("password"));
        return  model;
    }

    /**
     *  登录界面 手机登录的方法
     * @param user user对象
     * @param loginName 前台传回的用户名
     * @return user
     */
    public User loginByPhone(User user,String loginName) {
        String sql = "select * from t_user where phone = ? and password = ?";
        User model = dao.findFirst(sql,loginName, user.getStr("password"));
        return  model;
    }

    /**
     *  注册后自动登录的方法，
     * @param user user对象，包含phone password
     * @return user对象，包含uid
     */
    public User loginByPhone(User user){
        String sql = "select * from t_user where phone = ? and password = ?";
        User model = dao.findFirst(sql, user.getStr("phone"), user.getStr("password"));
        return  model;
    }


}
