package com.sers.backend.service.impl.admin.gps;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sers.backend.mapper.GpsMapper;
import com.sers.backend.mapper.UserMapper;
import com.sers.backend.pojo.*;
import com.sers.backend.service.admin.gps.GetlistAdminGpsService;
import com.sers.backend.utils.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class GetlistAdminGpsServiceImpl implements GetlistAdminGpsService {
    @Autowired
    private GpsMapper gpsMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public JSONObject getlist(Integer page) {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();
        JSONObject resp=new JSONObject();
        if(!user.getAdmin()){
            resp.put("message","无管理员权限");
            return resp;
        }
        IPage<Gps> gpsIPage=new Page<>(page,20);
        List<Gps> gpss=gpsMapper.selectPage(gpsIPage,null).getRecords();
        List<JSONObject> items=new LinkedList<>();
        for(Gps gps:gpss){
            JSONObject item=new JSONObject();
            if(gps.getUserId()==null){
                item.put("username",null);
            }else{
                User gps_user=userMapper.selectById(gps.getUserId());
                item.put("username",gps_user.getUsername());
            }
            item.put("gps",gps);
            items.add(item);
        }
        resp.put("message","successful");
        resp.put("items",items);
        resp.put("items_count",gpsMapper.selectCount(null));
        return resp;
    }
}
