package com.sers.backend.service.impl.shop;

import com.alibaba.fastjson.JSONObject;
import com.sers.backend.mapper.OrdMapper;
import com.sers.backend.mapper.ProductMapper;
import com.sers.backend.pojo.Ord;
import com.sers.backend.pojo.Product;
import com.sers.backend.pojo.User;
import com.sers.backend.service.shop.AddOrderService;
import com.sers.backend.utils.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AddOrderServiceImpl implements AddOrderService {
    @Autowired
    private OrdMapper ordMapper;
    @Autowired
    private ProductMapper productMapper;

    @Override
    public JSONObject add(Integer product_id, String number, String phone, String address, Integer method) {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();
        JSONObject resp=new JSONObject();
        Product product=productMapper.selectById(product_id);
        if(product==null){
            resp.put("message","产品不存在或已下架");
            return resp;
        }
        Integer number_num;
        try {
            number_num=Integer.parseInt(number);
        }catch (NumberFormatException e){
            resp.put("message","请输入正确购买数量");
            return resp;
        }
        if(number_num<=0||number_num>=100){
            resp.put("message","请输入正确购买数量");
            return resp;
        }
        if(phone==null||phone.length()==0){
            resp.put("message","电话号码不能为空");
            return resp;
        }
        if(!phone.matches("^1(3[0-9]|4[01456879]|5[0-35-9]|6[2567]|7[0-8]|8[0-9]|9[0-35-9])\\d{8}$")){
            resp.put("message","请输入正确电话号码");
            return resp;
        }
        if(address==null||address.length()==0){
            resp.put("message","收货地址不能为空");
            return resp;
        }
        if(address.length()>100){
            resp.put("message","收货地址长度不能大于100");
            return resp;
        }
        if(method!=0&&method!=1){
            resp.put("message","请选择支付方式");
            return resp;
        }
        Date now=new Date();
        Ord ord=new Ord(null,user.getId(),product_id,number_num,phone,address,number_num*product.getPrice(),method,now,null,null,null,1);
        ordMapper.insert(ord);
        resp.put("message","successful");
        return resp;
    }
}
