package com.sers.backend.service.impl.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sers.backend.mapper.DialogMapper;
import com.sers.backend.pojo.Dialog;
import com.sers.backend.pojo.User;
import com.sers.backend.service.service.GetlistDialogService;
import com.sers.backend.utils.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetlistDialogServiceImpl implements GetlistDialogService {
    @Autowired
     private DialogMapper dialogMapper;

    @Override
    public JSONObject getlist() {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();
        QueryWrapper<Dialog> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("send_user_id",user.getId()).or().eq("receive_user_id",user.getId());
        List<Dialog> dialogs=dialogMapper.selectList(queryWrapper);
        JSONObject resp=new JSONObject();
        resp.put("message","successful");
        resp.put("data",dialogs);
        return resp;
    }
}
