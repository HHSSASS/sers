package com.sers.backend.service.impl.monitor;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sers.backend.mapper.GpsMapper;
import com.sers.backend.pojo.Gps;
import com.sers.backend.pojo.User;
import com.sers.backend.service.monitor.GetlistGpsService;
import com.sers.backend.utils.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetlistGpsServiceImpl implements GetlistGpsService {
    @Autowired
    private GpsMapper gpsMapper;

    @Override
    public JSONObject getlist(Integer page) {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();
        JSONObject resp=new JSONObject();
        IPage<Gps> gpsIPage=new Page<>(page,20);
        QueryWrapper<Gps> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_id",user.getId());
        List<Gps> gpss=gpsMapper.selectPage(gpsIPage,queryWrapper).getRecords();
        resp.put("message","successful");
        resp.put("gpss",gpss);
        resp.put("gpss_count",gpsMapper.selectCount(null));
        return resp;
    }
}
