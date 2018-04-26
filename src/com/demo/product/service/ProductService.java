package com.demo.product.service;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;

public class ProductService {


    public List<Record> findByCid(int cid) {
        String sql="select * from t_product where cid = ?";
        List<Record> records = Db.find(sql, cid);
        return records;
    }

    public Record queryProductDetail(int pid) {
        String sql ="select * from t_product where pid = ?";
        Record product = Db.findFirst(sql, pid);
        return product;
    }

    public List<Record>  queryProductImg(int pid) {
        String sql ="select * from t_productImg where pid = ?";
        List<Record> records = Db.find(sql, pid);
        return records;
    }
}
