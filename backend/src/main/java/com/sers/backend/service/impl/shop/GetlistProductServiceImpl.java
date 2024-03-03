package com.sers.backend.service.impl.shop;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sers.backend.mapper.ProductMapper;
import com.sers.backend.pojo.Notice;
import com.sers.backend.pojo.Product;
import com.sers.backend.service.shop.GetlistProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetlistProductServiceImpl implements GetlistProductService {
    @Autowired
    private ProductMapper productMapper;

    @Override
    public JSONObject getlist(Integer page) {
        IPage<Product> productIPage=new Page<>(page,8);
        QueryWrapper<Notice> queryWrapper=new QueryWrapper<>();
        List<Product> products=productMapper.selectPage(productIPage,null).getRecords();
        JSONObject resp=new JSONObject();
        resp.put("message","successful");
        resp.put("products",products);
        resp.put("products_count",productMapper.selectCount(null));
        return resp;
    }
}
