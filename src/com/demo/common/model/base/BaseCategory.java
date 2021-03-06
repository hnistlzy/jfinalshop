package com.demo.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseCategory<M extends BaseCategory<M>> extends Model<M> implements IBean {

	public M setCid(java.lang.Integer cid) {
		set("cid", cid);
		return (M)this;
	}
	
	public java.lang.Integer getCid() {
		return getInt("cid");
	}

	public M setCname(java.lang.String cname) {
		set("cname", cname);
		return (M)this;
	}
	
	public java.lang.String getCname() {
		return getStr("cname");
	}

}
