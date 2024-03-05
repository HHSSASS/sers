package com.sers.backend.service.impl.user.order;

import com.alibaba.fastjson.JSONObject;
import com.sers.backend.mapper.OrdMapper;
import com.sers.backend.pojo.Ord;
import com.sers.backend.pojo.User;
import com.sers.backend.service.user.order.UpdateOrderService;
import com.sers.backend.utils.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UpdateOrderServiceImpl implements UpdateOrderService {
    @Autowired
    private OrdMapper ordMapper;

    @Override
    public JSONObject update(Integer id, Integer status) {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();
        JSONObject resp=new JSONObject();
        Ord ord=ordMapper.selectById(id);
        if(ord==null){
            resp.put("message","订单不存在");
            return resp;
        }
        if(status<=1||status>=5){
            resp.put("message","修改状态不正确");
            return resp;
        }
        if(ord.getStatus()==4){
            resp.put("message","该订单已退款");
            return resp;
        }
        Date send=null,finish=null,refund=null;
        if(status==2){
            if(ord.getStatus()==2){
                resp.put("message","该订单已发货");
                return resp;
            }
            if(ord.getStatus()==3){
                resp.put("message","该订单已完成");
                return resp;
            }
            if(!user.getAdmin()){
                resp.put("message","无管理员权限");
                return resp;
            }
            send=new Date();
        }
        if(status==3){
            if(ord.getStatus()==1){
                resp.put("message","该订单待发货");
                return resp;
            }
            if(ord.getStatus()==3){
                resp.put("message","该订单已完成");
                return resp;
            }
            if(ord.getUserId()!=user.getId()){
                resp.put("message","无权限");
                return resp;
            }
            finish=new Date();
        }
        if(status==4){
            if(!user.getAdmin()){
                resp.put("message","无管理员权限");
                return resp;
            }
            refund=new Date();
        }
        Ord new_ord=new Ord(ord.getId(),ord.getUserId(),ord.getProductId(),ord.getAddress(),ord.getNumber(),ord.getMethod(),ord.getCreateTime(),
                send,finish,refund,status);
        ordMapper.updateById(new_ord);
        resp.put("message","successful");
        return resp;
    }
}
