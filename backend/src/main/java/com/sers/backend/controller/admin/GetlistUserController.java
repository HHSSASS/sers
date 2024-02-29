package com.sers.backend.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.sers.backend.service.admin.GetlistUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetlistUserController {
    @Autowired
    private GetlistUserService getlistUserService;

    @GetMapping("/api/admin/getlistuser/")
    public JSONObject getlist(){
        return getlistUserService.getlist();
    }
}
