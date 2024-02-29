package com.sers.backend.service.impl.user.account;

import com.alibaba.fastjson.JSONObject;
import com.sers.backend.pojo.User;
import com.sers.backend.utils.UserDetailsImpl;
import com.sers.backend.service.user.account.LoginService;
import com.sers.backend.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Override
    public JSONObject login(String username, String password) {
        UsernamePasswordAuthenticationToken authenticationToken=
                new UsernamePasswordAuthenticationToken(username,password);
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        UserDetailsImpl loginUser=(UserDetailsImpl)authenticate.getPrincipal();
        User user=loginUser.getUser();
        String jwt= JwtUtil.createJWT(user.getId().toString());
        JSONObject resp= new JSONObject();
        resp.put("message","successful");
        resp.put("token",jwt);
        return resp;
    }
}
