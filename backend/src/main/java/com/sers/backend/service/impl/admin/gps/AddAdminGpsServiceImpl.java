package com.sers.backend.service.impl.admin.gps;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sers.backend.mapper.GpsMapper;
import com.sers.backend.pojo.Gps;
import com.sers.backend.pojo.User;
import com.sers.backend.service.admin.gps.AddAdminGpsService;
import com.sers.backend.utils.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AddAdminGpsServiceImpl implements AddAdminGpsService {
    @Autowired
    private GpsMapper gpsMapper;

    @Override
    public JSONObject add(String number, String type) {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();
        JSONObject resp=new JSONObject();
        if(!user.getAdmin()){
            resp.put("message","无管理员权限");
            return resp;
        }
        if(number==null||number.length()==0){
            resp.put("message","序列号不能为空");
            return resp;
        }
        if(number.length()>50){
            resp.put("message","序列号不正确");
            return resp;
        }
        if(type==null||type.length()==0){
            resp.put("message","型号不能为空");
            return resp;
        }
        if(type.length()>50){
            resp.put("message","型号不正确");
            return resp;
        }
        QueryWrapper<Gps> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("number",number);
        List<Gps> gpsList=gpsMapper.selectList(queryWrapper);
        if(!gpsList.isEmpty()){
            resp.put("message","序列号已存在");
            return resp;
        }
        Date now=new Date();
        Gps gps=new Gps(null,number,number,type,now,null,null);
        gpsMapper.insert(gps);
        resp.put("message","successful");
        return resp;
    }
}
