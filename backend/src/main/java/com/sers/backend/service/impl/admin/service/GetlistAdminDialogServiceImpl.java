package com.sers.backend.service.impl.admin.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sers.backend.mapper.DialogMapper;
import com.sers.backend.pojo.Dialog;
import com.sers.backend.pojo.User;
import com.sers.backend.service.admin.service.GetlistAdminDialogService;
import com.sers.backend.utils.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetlistAdminDialogServiceImpl implements GetlistAdminDialogService {
    @Autowired
    private DialogMapper dialogMapper;

    @Override
    public JSONObject getlist(Integer id) {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();
        JSONObject resp=new JSONObject();
        if(!user.getAdmin()){
            resp.put("message","无管理员权限");
            return resp;
        }
        QueryWrapper<Dialog> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("send_user_id",id).or().eq("receive_user_id",id);
        List<Dialog> dialogs=dialogMapper.selectList(queryWrapper);
        resp.put("message","successful");
        resp.put("data",dialogs);
        return resp;
    }
}
