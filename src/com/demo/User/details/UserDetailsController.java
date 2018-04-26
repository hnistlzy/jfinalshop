package com.demo.User.details;

import com.demo.common.model.User;
import com.demo.common.model.UserAddress;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;

@Before({UserDetailsInterceptor.class,UserDetailsPathInterceptor.class})
public class UserDetailsController extends Controller {
    UserDetailsService uds = new UserDetailsService();

    public void index() {
        render("/user/details/userDetail.html");
    }

    public void updateDetail() {
        User model = getModel(User.class);
        Integer uid = getParaToInt(0);
        User user = uds.updateDetails(model, uid.intValue());
        setSessionAttr("user", user);
        render("/user/details/userDetail.html");
    }

    public void queryRecords() {
        render("/index.html");
    }

    /**
     * 查询收货地址。
     */
    public void queryAddress() {
        Integer uid = getParaToInt(0);
        List<Record> addrList = uds.queryAddress(uid);
        //form表单action内容，添加地址action =>addAddress
        setAttr("path", "/user/details/addAddress");
        setAttr("size", addrList.size());
        setAttr("addrList", addrList);
        render("/user/details/address.html");
    }

    /**
     * 添加收货地址。
     */
    public void addAddress(){
        int uid = getParaToInt(0);
        UserAddress userAddress = getModel(UserAddress.class);
        userAddress.set("uid",uid);
        userAddress.save();
        List<Record> addrList = uds.queryAddress(uid);
        setAttr("size", addrList.size());
        //解决添加一个address后，action中没有内容的bug
        setAttr("path","/user/details/addAddress");
        setAttr("addrList", addrList);
        render("/user/details/address.html");
    }
    /**
     * 查询订单
     */
    public void queryMyOrder() {
        render("/index.html");
    }
    /**
     * 点击修改后，将信息展示在上方form中
     */
    public void changAddress() {
        int addrId = getParaToInt(0);
        int uid = getParaToInt(1);
        Record addr = uds.queryByUid_AddrId(uid, addrId);
        List<Record> addrList = uds.queryAddress(uid);
        setAttr("addrList", addrList);
        setAttr("size",addrList.size());
        setAttr("addr", addr);
        //from表单action路径,修改地址的action =>updateAddress
        setAttr("path", "/user/details/updateAddress");
        render("/user/details/address.html");

    }

    /**
     * 更新展示在form表单中的内容
     */
    public void updateAddress() {
        int addrId = getParaToInt(1);
        int uid = getParaToInt(0);
        UserAddress userAddress = getModel(UserAddress.class);
        uds.updateAddress(userAddress, addrId);
        List<Record> records = uds.queryAddress(uid);
        setAttr("addrList", records);
        render("/user/details/address.html");
    }
    public void deleteAddress(){
        int addrId = getParaToInt(0);
        int uid = getParaToInt(1);
        uds.deleteAddress(addrId);
        List<Record> addrList = uds.queryAddress(uid);
        setAttr("size",addrList.size());
        setAttr("addrList",addrList);
        render("/user/details/address.html");
    }

    /**
     * 默认收货地址思路：
     *  1、点击链接时，访问setDefaultAddr方法，传参：addrId,uid
     *  2、根据uid查询数据库中，其他地址有没有 addrid=1的，
     *  3、如果有，则将该记录的isDefault字段更新为0，
     *  4、将传过来的addId对应的isDefault字段更新为1
     */
    public void setDefaultAddr(){
        int addrId = getParaToInt(0);
        int uid = getParaToInt(1);
        //根据uid查询地址list
        List<Record> addrList = uds.queryAddress(uid);
        //遍历List并寻找isDefault =1 的字段
        int defaultAddrID = getDefaultAddrID(addrList);
        if(defaultAddrID ==0){
            //等于0,表示没有这样的字段，可以直接更新
            uds.setDefaultAddr(addrId,1);
            //更新完成后，回显数据，可能要再次从数据库中查询内容
            setAttr("size", addrList.size());
            //解决添加一个address后，action中没有内容的bug
            setAttr("path","/user/details/addAddress");
            List<Record> records = uds.queryAddress(uid);
            setAttr("addrList", records);
            render("/user/details/address.html");

        }else{
            //存在，则先将该addrID的isDefault字段设置为0，再更新字段
            uds.setDefaultAddr(defaultAddrID,0);
            uds.setDefaultAddr(addrId,1);
            setAttr("size", addrList.size());
            //解决添加一个address后，action中没有内容的bug
            setAttr("path","/user/details/addAddress");
            List<Record> records = uds.queryAddress(uid);
            setAttr("addrList", records);
            render("/user/details/address.html");
        }
    }
    public int getDefaultAddrID(List<Record> recordList){
        int defaultAddrID=0;
        for(Record addr : recordList){
            if (addr.getInt("isDefault")!=0 && addr.getInt("isDefault")!=null){
                defaultAddrID = addr.getInt("addrId");
            }
        }
        return  defaultAddrID;
    }
}
