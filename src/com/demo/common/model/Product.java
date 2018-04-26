package com.demo.common.model;

import com.jfinal.plugin.activerecord.Model;

public class Product  extends Model<Product> {

    int pid;
    String pname;
    float price; //跟数据库中的单位不一致，可能会出问题
    String title_img;
    String headline;
    String subtitle;
    int cid;
    String detail_img;
    int isHot;
    int isRecommend;
    float originalPrice;
    int score;
}
