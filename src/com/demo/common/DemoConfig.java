package com.demo.common;


import com.demo.User.TestController;
import com.demo.User.UserController;
import com.demo.User.details.UserDetailsController;
import com.demo.common.model.*;
import com.demo.index.IndexController;
import com.demo.product.controller.CartController;
import com.demo.product.controller.OrderController;
import com.demo.product.controller.ProductController;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.ext.interceptor.SessionInViewInterceptor;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.template.Engine;
import com.jfplugin.mail.MailPlugin;


/**
 * 本 demo 仅表达最为粗浅的 jfinal 用法，更为有价值的实用的企业级用法
 * 详见 JFinal 俱乐部: http://jfinal.com/club
 * 
 * API引导式配置
 */
public class DemoConfig extends JFinalConfig {
	
	/**
	 * 运行此 main 方法可以启动项目，此main方法可以放置在任意的Class类定义中，不一定要放于此
	 * 
	 * 使用本方法启动过第一次以后，会在开发工具的 debug、run config 中自动生成
	 * 一条启动配置，可对该自动生成的配置再添加额外的配置项，例如 VM argument 可配置为：
	 * -XX:PermSize=64M -XX:MaxPermSize=256M
	 */
	public static void main(String[] args) {

		/**
		 * 特别注意：IDEA 之下建议的启动方式，仅比 eclipse 之下少了最后一个参数
		 */
	 JFinal.start("WebRoot", 80, "/");
	}
	
	/**
	 * 配置常量
	 */
	public void configConstant(Constants me) {
		// 加载少量必要配置，随后可用PropKit.get(...)获取值
		PropKit.use("a_little_config.txt");
		me.setDevMode(PropKit.getBoolean("devMode", false));

	}
	
	/**
	 * 配置路由
	 */
	public void configRoute(Routes me) {
		me.add("/test",TestController.class);
		me.add("/", IndexController.class);	// 第三个参数为该Controller的视图存放路径
		me.add("/user", UserController.class);// 第三个参数省略时默认与第一个参数值相同，在此即为 "/blog"
		me.add("/user/details",UserDetailsController.class);
		me.add("/product",ProductController.class);
		me.add("/cart",CartController.class);
		me.add("/order",OrderController.class);
	}
	
	public void configEngine(Engine me) {
		me.addSharedFunction("/end.html");
		me.addSharedFunction("/personal_left.html");
		me.addSharedFunction("/top.html");
	}
	
	public static DruidPlugin createDruidPlugin() {
		return new DruidPlugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password").trim());
	}
	
	/**
	 * 配置插件
	 */
	public void configPlugin(Plugins me) {
		// 配置C3p0数据库连接池插件
		DruidPlugin druidPlugin = createDruidPlugin();
		me.add(druidPlugin);
		//邮件发送插件
		me.add(new MailPlugin(PropKit.use("mail.properties").getProperties()));
		// 配置ActiveRecord插件
		ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
		// 所有映射在 MappingKit 中自动化搞定
		arp.addMapping("t_user","uid", User.class);
		arp.addMapping("t_address","addrId",UserAddress.class);
		arp.addMapping("t_category","cid",Category.class);
		arp.addMapping("t_productImg","pimgID",ProductImg.class);
		arp.addMapping("t_cart","cartID",Cart.class);
		arp.setShowSql(true);
		me.add(arp);
	}
	
	/**
	 * 配置全局拦截器
	 */
	public void configInterceptor(Interceptors me) {
		//不加的话，在enjoy中无法获得session
		me.add(new SessionInViewInterceptor());
	}
	
	/**
	 * 配置处理器
	 */
	public void configHandler(Handlers me) {
		me.add(new ContextPathHandler("contextPath"));
	}
}
