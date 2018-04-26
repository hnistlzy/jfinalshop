package com.demo.User.details;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;

public class UserDetailsInterceptor implements Interceptor {
    @Override
    public void intercept(Invocation inv) {
        Controller c = inv.getController();
        Object user = c.getSession().getAttribute("user");
        if(user !=null){
            //正常跳转
            c.render("/user/detail/userDetail.html");
            inv.invoke();
        }else{
            //跳转到注册页
            c.render("/user/login.html");
            return;
        }

    }
}
