package com.sers.backend.service.impl.admin.dialog;

import com.alibaba.fastjson.JSONObject;
import com.sers.backend.mapper.DialogMapper;
import com.sers.backend.pojo.Dialog;
import com.sers.backend.pojo.User;
import com.sers.backend.service.admin.dialog.AddAdminDialogService;
import com.sers.backend.utils.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AddAdminDialogServiceImpl implements AddAdminDialogService {
    @Autowired
    private DialogMapper dialogMapper;

    @Override
    public JSONObject add(Integer id, String content) {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();
        JSONObject resp=new JSONObject();
        if(!user.getAdmin()){
            resp.put("message","无管理员权限");
            return resp;
        }
        if(content.length()==0) {
            resp.put("message", "内容不能为空");
            return resp;
        }
        if(content.length()>500){
            resp.put("message","内容长度不能大于500");
            return resp;
        }
        Date now=new Date();
        Dialog dialog=new Dialog(null,0,id,content,now,false);
        dialogMapper.insert(dialog);
        resp.put("message","successful");
        return resp;
    }
}
