package com.sers.backend.service.impl.service;

import com.alibaba.fastjson.JSONObject;
import com.sers.backend.mapper.DialogMapper;
import com.sers.backend.pojo.Dialog;
import com.sers.backend.pojo.User;
import com.sers.backend.service.service.AddDialogService;
import com.sers.backend.utils.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AddDialogServiceImpl implements AddDialogService {
    @Autowired
    private DialogMapper dialogMapper;

    @Override
    public JSONObject add(String content) {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();
        JSONObject resp=new JSONObject();
        if(content.length()==0) {
            resp.put("message", "内容不能为空");
            return resp;
        }
        if(content.length()>500){
            resp.put("message","内容长度不能大于500");
            return resp;
        }
        Date now=new Date();
        Dialog dialog=new Dialog(null,user.getId(),0,content,now,false);
        dialogMapper.insert(dialog);
        resp.put("message","successful");
        return resp;
    }
}
