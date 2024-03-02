package com.sers.backend.service.impl.admin.gps;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sers.backend.mapper.GpsMapper;
import com.sers.backend.pojo.Gps;
import com.sers.backend.pojo.Notice;
import com.sers.backend.pojo.User;
import com.sers.backend.service.admin.gps.GetlistAdminGpsService;
import com.sers.backend.utils.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetlistAdminGpsServiceImpl implements GetlistAdminGpsService {
    @Autowired
    private GpsMapper gpsMapper;

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
        resp.put("message","successful");
        resp.put("gpss",gpss);
        resp.put("gpss_count",gpsMapper.selectCount(null));
        return resp;
    }
}
