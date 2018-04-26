package com.demo.product.controller;

import com.demo.common.model.Product;
import com.demo.product.service.ProductService;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;

public class ProductController  extends Controller{
    ProductService ps=new ProductService();
    public void index(){
        render("/product/product.html");
    }
    public void findByCid(){
        int cid = getParaToInt(0);
        List<Record> productList = ps.findByCid(cid);
        setAttr("productList",productList);
        render("/product/productList.html");
    }
    public void queryProductDetail(){
        int pid = getParaToInt(0);
        //查询商品的信息
        Record product = ps.queryProductDetail(pid);
        //查询商品的描述图片
        List<Record> productImg = ps.queryProductImg(pid);
        setAttr("product",product);
        setAttr("productImg",productImg);
        render("/product/product.html");
    }
}
