package com.sers.backend.service.impl.user.order;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sers.backend.mapper.OrdMapper;
import com.sers.backend.mapper.ProductMapper;
import com.sers.backend.mapper.UserMapper;
import com.sers.backend.pojo.Ord;
import com.sers.backend.pojo.Product;
import com.sers.backend.pojo.User;
import com.sers.backend.service.user.order.GetlistOrderService;
import com.sers.backend.utils.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class GetlistOrderServiceImpl implements GetlistOrderService {
    @Autowired
    private OrdMapper ordMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ProductMapper productMapper;

    @Override
    public JSONObject getlist(Integer page, Integer condition) {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();
        JSONObject resp=new JSONObject();
        if(condition<0||condition>4){
            resp.put("message","请选择正确筛选条件");
            return resp;
        }
        IPage<Ord> ordIPage=new Page<>(page,20);
        QueryWrapper<Ord> queryWrapper=new QueryWrapper<>();
        queryWrapper.orderByDesc("id").eq("user_id",user.getId());
        if(condition>0){
            queryWrapper.eq("status",condition);
        }
        List<Ord> ords=ordMapper.selectPage(ordIPage,queryWrapper).getRecords();
        List<JSONObject> items=new LinkedList<>();
        for(Ord ord:ords){
            JSONObject item=new JSONObject();
            User ord_user=userMapper.selectById(ord.getUserId());
            Product product=productMapper.selectById(ord.getProductId());
            if(product==null){
                item.put("product_name",null);
            }else{
                item.put("product_name",product.getName());
            }
            item.put("order",ord);
            item.put("username",ord_user.getUsername());
            items.add(item);
        }
        resp.put("message","successful");
        resp.put("items",items);
        resp.put("items_count",ordMapper.selectCount(queryWrapper));
        return resp;
    }
}
