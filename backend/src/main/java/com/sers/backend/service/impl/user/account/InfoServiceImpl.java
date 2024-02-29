package com.sers.backend.service.impl.user.account;

import com.alibaba.fastjson.JSONObject;
import com.sers.backend.pojo.User;
import com.sers.backend.utils.UserDetailsImpl;
import com.sers.backend.service.user.account.InfoService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class InfoServiceImpl implements InfoService {
    @Override
    public JSONObject getinfo() {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();
        JSONObject resp=new JSONObject();
        resp.put("message", "successful");
        resp.put("id", user.getId().toString());
        resp.put("username", user.getUsername());
        resp.put("admin",user.getAdmin());
        return resp;
    }
}
