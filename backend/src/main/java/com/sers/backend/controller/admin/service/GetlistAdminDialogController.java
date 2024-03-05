package com.sers.backend.controller.admin.service;

import com.alibaba.fastjson.JSONObject;
import com.sers.backend.service.admin.service.GetlistAdminDialogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GetlistAdminDialogController {
    @Autowired
    private GetlistAdminDialogService getlistServiceDialogService;

    @GetMapping("/api/admin/dialog/getlist/")
    public JSONObject getlist(@RequestParam Map<String,String> data){
        Integer id=Integer.parseInt(data.get("id"));
        return getlistServiceDialogService.getlist(id);
    }
}
