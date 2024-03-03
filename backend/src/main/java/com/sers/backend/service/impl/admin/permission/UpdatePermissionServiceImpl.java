package com.sers.backend.service.impl.admin.permission;

import com.alibaba.fastjson.JSONObject;
import com.sers.backend.mapper.UserMapper;
import com.sers.backend.pojo.User;
import com.sers.backend.service.admin.permission.UpdatePermissionService;
import com.sers.backend.utils.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UpdatePermissionServiceImpl implements UpdatePermissionService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public JSONObject add(Integer id) {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();
        JSONObject resp=new JSONObject();
        if(!user.getAdmin()){
            resp.put("message","无管理员权限");
            return resp;
        }
        User admin_user=userMapper.selectById(id);
        if(admin_user.getAdmin()){
            resp.put("message","该用户已是管理员");
            return resp;
        }
        User new_user=new User(user.getId(),user.getUsername(),user.getPassword(),false);
        User new_admin_user=new User(admin_user.getId(),admin_user.getUsername(),admin_user.getPassword(),true);
        userMapper.updateById(new_user);
        userMapper.updateById(new_admin_user);
        resp.put("message","successful");
        return resp;
    }
}
