package com.demo.User.interceptor;


import com.demo.common.model.User;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;

public class LoginInterceptor implements Interceptor {
    @Override
    public void intercept(Invocation inv) {
        Controller controller = inv.getController();
        String loginName = controller.getPara("loginName");
        User model = controller.getModel(User.class);
        if(loginName == null || model.getStr("password")==null){
            controller.render("/user/login.html");
            return;
        }else{
            inv.invoke();
        }

    }
}
