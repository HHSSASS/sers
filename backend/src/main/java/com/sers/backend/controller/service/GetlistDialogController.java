package com.sers.backend.controller.service;

import com.alibaba.fastjson.JSONObject;
import com.sers.backend.service.service.GetlistDialogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetlistDialogController {
    @Autowired
    private GetlistDialogService getlistDialogService;

    @GetMapping("/api2/dialog/getlist/")
    public JSONObject getlist(){
        return getlistDialogService.getlist();
    }
}
