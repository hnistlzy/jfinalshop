package com.demo.product.controller;

import com.jfinal.core.Controller;

public class CartController extends Controller {
    public void index(){
        render("/cart/cart.html");
    }
    public void addToCart(){
        int pid = getParaToInt(0);
        

        render("/cart/cart.html");
    }
}
