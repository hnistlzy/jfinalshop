package com.demo.User;

import com.demo.common.model.User;
import com.jfinal.captcha.CaptchaRender;
import com.jfinal.core.Controller;

public class TestController  extends Controller {
    public void index(){
        User dao = new User().dao();
        String sql="select * from t_user where phone = ? and password = ?";
        User first = dao.findFirst(sql, "13786093812", "123456");
        System.out.println(first);
        render("/index.html");

    }
}
