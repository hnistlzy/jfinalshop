package com.demo.index;

import com.demo.common.model.Category;
import com.jfinal.core.Controller;

import java.util.List;

/**
 * 本 demo 仅表达最为粗浅的 jfinal 用法，更为有价值的实用的企业级用法
 * 详见 JFinal 俱乐部: http://jfinal.com/club
 * 
 * IndexController
 */
public class IndexController extends Controller {
     IndexService indexService=new IndexService();
	public void index() {
	    //页面加载时查询所有的一级分类并存到session中
        List<Category> categoryList = indexService.queryCategory();
        setSessionAttr("categoryList",categoryList);
        render("index.html");
	}
	public void reg(){
		render("/user/reg.html");
	}
	public void login(){
		render("/user/login.html");
	}

}





