package com.demo.User.details;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;

public class UserDetailsPathInterceptor implements Interceptor {
    @Override
    public void intercept(Invocation inv) {
        Controller controller = inv.getController();
        String viewPath = controller.getViewPath();

        System.out.println(viewPath);
        inv.invoke();
    }
}
