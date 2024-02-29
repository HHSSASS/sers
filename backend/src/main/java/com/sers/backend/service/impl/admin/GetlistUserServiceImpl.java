package com.sers.backend.service.impl.admin;

import com.alibaba.fastjson.JSONObject;
import com.sers.backend.mapper.UserMapper;
import com.sers.backend.pojo.User;
import com.sers.backend.service.admin.GetlistUserService;
import com.sers.backend.utils.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetlistUserServiceImpl implements GetlistUserService {
    @Autowired
    private UserMapper userMapper;

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
        resp.put("message","successful");
        resp.put("data",users);
        return resp;
    }
}
