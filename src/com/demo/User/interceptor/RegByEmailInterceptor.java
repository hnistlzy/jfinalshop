package com.demo.User.interceptor;

import com.demo.User.UserService;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

public class RegByEmailInterceptor implements Interceptor {
    @Override
    public void intercept(Invocation inv) {
        String email = inv.getController().getPara("email");
        System.out.println(email);
        if(email!=null) {
            Boolean bool = new UserService().ajaxValidateEmail(email); //返回false=》没有被注册
            if (bool) {
                inv.getController().setAttr("mailError", "该邮箱已经被注册");
                inv.getController().render("/reg");
                return;
            }
            inv.invoke();
        }else{
            System.out.println("我要跳转了");
            inv.getController().render("/user/reg.html");
            return;
        }
        inv.invoke();

    }
}
