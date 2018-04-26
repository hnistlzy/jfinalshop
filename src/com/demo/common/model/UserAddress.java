package com.demo.common.model;

import com.jfinal.plugin.activerecord.Model;

public class UserAddress extends Model<UserAddress> {
    int addrId;
    String province;
    String city;
    String detailAddr;
    String consignee; //收货人
    String consigneePhone; //收货人电话
    int isDefault;  //是否时默认收货地址
    int uid;    //外键，uid
}
