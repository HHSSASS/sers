package com.sers.backend.controller.user.account;

import com.alibaba.fastjson.JSONObject;
import com.sers.backend.service.user.account.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class InfoController {
    @Autowired
    private InfoService infoService;
    @GetMapping("/api2/user/account/info/")
    public JSONObject getinfo(){
        return infoService.getinfo();
    }
}
