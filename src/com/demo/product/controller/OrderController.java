package com.demo.product.controller;

import com.jfinal.core.Controller;

public class OrderController  extends Controller {
    public void index(){
        render("/order/order.html");
    }
}
