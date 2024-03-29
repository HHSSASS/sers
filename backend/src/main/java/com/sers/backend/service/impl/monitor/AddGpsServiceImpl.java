package com.sers.backend.service.impl.monitor;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sers.backend.mapper.GpsMapper;
import com.sers.backend.pojo.Gps;
import com.sers.backend.pojo.User;
import com.sers.backend.service.monitor.AddGpsService;
import com.sers.backend.utils.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AddGpsServiceImpl implements AddGpsService {
    @Autowired
    private GpsMapper gpsMapper;

    @Override
    public JSONObject add(String number) {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();
        JSONObject resp=new JSONObject();
        if(number==null||number.length()==0){
            resp.put("message","序列号不能为空");
            return resp;
        }
        if(number.length()>50){
            resp.put("message","序列号不正确");
            return resp;
        }
        QueryWrapper<Gps> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("number",number);
        Gps gps=gpsMapper.selectOne(queryWrapper);
        if(gps==null){
            resp.put("message","序列号不存在");
            return resp;
        }
        if(gps.getUserId()!=null){
            resp.put("message","该序列号已被激活");
            return resp;
        }
        Date now=new Date();
        Gps new_gps=new Gps(gps.getId(),gps.getNumber(),gps.getNumber(),gps.getType(),gps.getAddTime(),user.getId(),now);
        gpsMapper.updateById(new_gps);
        resp.put("message","successful");
        return resp;
    }
}
