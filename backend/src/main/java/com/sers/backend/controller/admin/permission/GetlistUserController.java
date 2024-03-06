package com.sers.backend.controller.admin.permission;

import com.alibaba.fastjson.JSONObject;
import com.sers.backend.service.admin.permission.GetlistUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetlistUserController {
    @Autowired
    private GetlistUserService getlistUserService;

    @GetMapping("/api2/admin/user/getlist/")
    public JSONObject getlist(){
        return getlistUserService.getlist();
    }
}
