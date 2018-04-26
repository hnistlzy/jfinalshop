package com.demo.index;

import com.demo.common.model.Category;


import java.util.List;

public class IndexService {
    public List<Category> queryCategory() {
        String sql ="select * from t_category";
        Category dao = new Category().dao();
        List<Category> categoryList = dao.find(sql);
        return categoryList;
    }
}
