package com.demo.User.details;

import com.demo.common.model.User;
import com.demo.common.model.UserAddress;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;

public class UserDetailsService {
    public static User dao = new User().dao();

    public User queryDetailsById(Integer uid) {
        User user = dao.findById(uid);
        return user;
    }

    public User updateDetails(User model, int uid) {
        String sql = "update t_user set nickname= ? ,realname = ? ,age = ? ,phone = ? ,email = ? where uid = ?";
        String nickname = model.getStr("nickname");
        String realname = model.getStr("realname");
        String age = model.getStr("age");
        String phone = model.getStr("phone");
        String email = model.getStr("email");
        Db.update(sql, nickname, realname, age, phone, email, uid);

        //更新model
        User user = dao.findById(uid);
        return user;

    }

    public List<Record> queryAddress(int uid) {
        String sql = "select * from t_address where uid = ? ";
        List<Record> records = Db.find(sql, uid);

        return records;


    }

    public Record queryByUid_AddrId(int uid, int addrId) {
        String sql = "select * from t_address where uid = ? and addrId = ?";
        Record addr = Db.findFirst(sql, uid, addrId);
        return addr;
    }

    public void updateAddress(UserAddress userAddress, int addrId) {
        String sql = "update t_address set province = ? ,city = ? , detailAddr = ? ,consignee = ? , consigneePhone = ? where addrId = ?";
        String province = userAddress.getStr("province");
        String city = userAddress.getStr("city");
        String detailAddr = userAddress.getStr("detailAddr");
        String consignee = userAddress.getStr("consignee");
        String consigneePhone = userAddress.getStr("consigneePhone");
        int update = Db.update(sql, province, city, detailAddr, consignee, consigneePhone, addrId);
        System.out.println(update);
    }

    public void deleteAddress(int addrId) {
        String sql="delete from t_address where addrId = ?";
        int delete = Db.delete(sql, addrId);


    }

    public void setDefaultAddr(int addrId ,int defaultValue) {
        String sql="update t_address set isDefault = ?  where addrId = ?";
        Db.update(sql,defaultValue,addrId);
    }
}
