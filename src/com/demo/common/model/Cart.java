package com.demo.common.model;

import com.jfinal.plugin.activerecord.Model;

public class Cart extends Model<Cart> {
    int cartID;
    String img;
    String headline;
    String amount;
    float price;
    int pid;
    int uid;
}
