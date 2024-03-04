package com.sers.backend.service.impl.monitor;

import com.alibaba.fastjson.JSONObject;
import com.sers.backend.mapper.GpsMapper;
import com.sers.backend.pojo.Gps;
import com.sers.backend.pojo.User;
import com.sers.backend.service.monitor.UpdateGpsService;
import com.sers.backend.utils.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UpdateGpsServiceImpl implements UpdateGpsService {
    @Autowired
    private GpsMapper gpsMapper;

    @Override
    public JSONObject update(Integer id, String name) {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();
        JSONObject resp=new JSONObject();
        if(name==null||name.length()==0){
            resp.put("message","名称不能为空");
            return resp;
        }
        if(name.length()>10){
            resp.put("message","名称长度不能大于10");
            return resp;
        }
        Gps gps=gpsMapper.selectById(id);
        if(gps==null){
            resp.put("message","芯片不存在");
            return resp;
        }
        if(gps.getUserId()!= user.getId()){
            resp.put("message","没有权限修改该芯片");
        }
        Gps new_gps=new Gps(gps.getId(),name, gps.getNumber(), gps.getType(), gps.getAddTime(), gps.getUserId(), gps.getActiveTime());
        gpsMapper.updateById(new_gps);
        resp.put("message","successful");
        return resp;
    }
}
