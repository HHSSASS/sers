package com.sers.backend.controller.user.account;

import com.alibaba.fastjson.JSONObject;
import com.sers.backend.service.user.account.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;
    @PostMapping("/api/user/account/login/")
    public JSONObject login(@RequestParam Map<String,String> map){
        String username=map.get("username");
        String password=map.get("password");
        return loginService.login(username,password);
    }
}
