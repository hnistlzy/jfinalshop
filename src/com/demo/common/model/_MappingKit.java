package com.demo.common.model;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;

/**
 * Generated by JFinal, do not modify this file.
 * <pre>
 * Example:
 * public void configPlugin(Plugins me) {
 *     ActiveRecordPlugin arp = new ActiveRecordPlugin(...);
 *     _MappingKit.mapping(arp);
 *     me.add(arp);
 * }
 * </pre>
 */
public class _MappingKit {
	
	public static void mapping(ActiveRecordPlugin arp) {
		arp.addMapping("t_address", "addrId", Address.class);
		arp.addMapping("t_cart", "cartID", Cart.class);
		arp.addMapping("t_category", "cid", Category.class);
		arp.addMapping("t_product", "pid", Product.class);
		arp.addMapping("t_productimg", "pimgID", ProductImg.class);
		arp.addMapping("t_user", "uid", User.class);
	}
}
