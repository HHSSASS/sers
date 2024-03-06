package com.sers.backend.service.impl.admin.dialog;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sers.backend.mapper.DialogMapper;
import com.sers.backend.mapper.UserMapper;
import com.sers.backend.pojo.Dialog;
import com.sers.backend.pojo.User;
import com.sers.backend.service.admin.dialog.GetlistDialogUserService;
import com.sers.backend.service.admin.permission.GetlistUserService;
import com.sers.backend.utils.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class GetlistDialogUserServiceImpl implements GetlistDialogUserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DialogMapper dialogMapper;

    @Override
    public JSONObject getlist() {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();
        JSONObject resp=new JSONObject();
        if(!user.getAdmin()){
            resp.put("message","无管理员权限");
            return resp;
        }
        List<User> users=userMapper.selectList(null);
        List<JSONObject> items=new LinkedList<>();
        for(User dialog_user:users){
            JSONObject item=new JSONObject();
            QueryWrapper<Dialog> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("send_user_id",dialog_user.getId()).eq("is_read",0);
            item.put("user",dialog_user);
            item.put("count",dialogMapper.selectCount(queryWrapper));
            items.add(item);
        }
        resp.put("message","successful");
        resp.put("items",items);
        return resp;
    }
}
