package com.demo.User;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;


import com.demo.User.interceptor.LoginInterceptor;
import com.demo.User.interceptor.RegByEmailInterceptor;
import com.demo.common.FindMail;
import com.demo.common.SmsDemo;
import com.demo.common.model.User;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;


public class UserController  extends Controller {
   UserService userService =new UserService();

  @ Before(RegByEmailInterceptor.class)
    public void regByEmail(){
       User user = getModel(User.class);
       Boolean bool = userService.regByEmail(user);
       if(bool){
            setAttr("flag",true);
            String email= new FindMail().getMail(user.getStr("email"));
           StringBuilder sb = new StringBuilder();
           sb.append("http://mail.").append(email).append(".com");
           setAttr("regEmail",user.getStr("email"));
           setAttr("mail",sb.toString());
           setAttr("msg","注册成功，请前往邮箱激活！！！！");
       }else{
           setAttr("flag",false);
           setAttr("msg","注册失败");
       }
       render("/user/msg.html");

    }
    public  void regByPhone(){
        User user = getModel(User.class);
        String verifyCode = getPara("verifyCode");
        String code = getSession().getAttribute("code").toString();
        if (verifyCode.equals(code)){
            user.save();
            User model = userService.loginByPhone(user);
            setSessionAttr("user",model);
            render("/index.html");
        }else{
            setAttr("user",user);
            setAttr("phoneCodeError","验证码输入不正确");
            render("/user/login.html");
        }

    }
    /**
     *  发送短信的方法
     */
    public void ajaxGetPhoneCode() throws InterruptedException ,ClientException {

        //发送验证码,如果发送成功就返回一个
        String phone = getPara("phone");
        SendSmsResponse   response = SmsDemo.sendSms(phone);
        String content = SmsDemo.querySendDetails(response, phone);
        String code =  content.substring(content.length() - 14, content.length() - 8);
        setSessionAttr("code", code);
        renderJson("true");
    }

    /**
     * 异步校验手机号是否已经被注册过
     */
    public void ajaxValidatePhoneNum(){
        String phone = getPara("phone");
        Boolean bool = userService.ajaxValidatePhoneNum(phone);
        if(bool){
            //手机号已经被注册，校验失败
            renderJson("false");
        }else {
            //手机号尚未注册，校验成功
            renderJson("true");
        }
    }

    /**
     * 异步校验邮箱是否被注册过
     */
    public void ajaxValidateEmail(){
        String email = getPara("email");
        Boolean bool = userService.ajaxValidateEmail(email);
        if(bool){
            //已经被注册，校验失败
            renderJson("false");
        }else{
            System.out.println("没有被注册");
            renderJson("true");
        }

    }
    /**
     * 生成验证码
     */
    public void getVerifyCode(){
       renderCaptcha();
    }

    /**
     * 邮箱注册完成后，为用户自动登录
     */
    public  void  activate(){
        String str = getPara(0);
        boolean bool = userService.queryByActvationCode(str);
        if(bool){
            //激活成功
            setAttr("flag",true);
            setAttr("autoLogin",true);
            setAttr("msg","激活成功，正在为您登陆");
            setAttr("loginName","user.email");
            setAttr("username",userService.queryUserByActvationCode(str).getStr("email"));
            setAttr("password",userService.queryUserByActvationCode(str).getStr("password"));
            render("/user/msg.html");
        }else{
            //激活失败
            setAttr("flag",false);
            setAttr("msg","激活失败，用户已经激活");
            render("/user/msg.html");
        }


    }

    /**
     * 登录的通用方法
     */
    @Before(LoginInterceptor.class)
    public void login(){
        User user = getModel(User.class);
        String loginName = getPara("loginName");
        if(findCode(loginName)){
            //如果有"@"符号，调用邮箱的登录方法
            User model = userService.loginByEmail(user, loginName);
            if(model!=null && model.getInt("isActivated") ==1){
                setSessionAttr("loginName",loginName);
                setSessionAttr("user",model);
                render("/index.html");
            }else{
                setAttr("loginName",loginName);
                setAttr("loginError","账号或密码错误或未激活");
                render("/user/login.html");
            }
        }else{
            User model = userService.loginByPhone(user, loginName);
            if(model!=null&&model.getInt("uid")!=null){
                setSessionAttr("loginName",loginName);
                setSessionAttr("user",model);
                render("/index.html");
            }else{
                setAttr("loginName",loginName);
                setAttr("loginError","账号或密码错误");
                render("/user/login.html");
            }
        }

    }

    /**
     * 邮箱注册后自动登录
     */
    public void autoLogin(){
        User model = getModel(User.class);
        String sql ="select * from t_user where email =? and password =?";
        User user = new User().dao().findFirst(sql, model.getStr("email"), model.getStr("password"));
        setSessionAttr("user",user);
        render("/index.html");
    }

    /**
     * 找"@"符号
     * @param str 邮箱
     * @return true=>有@符号
     */
    private boolean findCode(String str) {
        boolean bool=false;
        int length=str.length();
        for(int i=0;i<length;i++){
            bool  = str.substring(i, i+1).equals("@");
            if(bool){
                return bool;
            }
        }
        return bool;
    }
}
