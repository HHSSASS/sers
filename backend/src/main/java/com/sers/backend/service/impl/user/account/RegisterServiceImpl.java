package com.sers.backend.service.impl.user.account;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sers.backend.mapper.UserMapper;
import com.sers.backend.pojo.User;
import com.sers.backend.service.user.account.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public JSONObject register(String username, String password, String confirmPassword) {
        JSONObject resp=new JSONObject();
        if(username==null) {
            resp.put("message", "用户名不能为空");
            return resp;
        }
        username=username.trim();
        if(username.length()==0) {
            resp.put("message", "用户名不能为空");
            return resp;
        }
        if(username.length()>100){
            resp.put("message","用户名长度不能大于100");
            return resp;
        }
        if(password==null||confirmPassword==null){
            resp.put("message", "密码不能为空");
            return resp;
        }
        if(password.length()==0||confirmPassword.length()==0){
            resp.put("message", "密码不能为空");
            return resp;
        }
        if(password.length()>100){
            resp.put("message","密码长度不能大于100");
            return resp;
        }
        if(!password.equals(confirmPassword)){
            resp.put("message","两次输入的密码不一致");
            return resp;
        }
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("username",username);
        List<User> users=userMapper.selectList(queryWrapper);
        if(!users.isEmpty()){
            resp.put("message","用户名已存在");
            return resp;
        }

        String encodedPassword=passwordEncoder.encode(password);
        User user=new User(null,username,encodedPassword,false);
        userMapper.insert(user);
        resp.put("message","successful");
        return resp;
    }
}
