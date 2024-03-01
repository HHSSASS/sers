package com.sers.backend.controller.service;

import com.alibaba.fastjson.JSONObject;
import com.sers.backend.service.service.AddDialogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AddDialogController {
    @Autowired
    private AddDialogService addDialogService;

    @PostMapping("/api/service/add")
    public JSONObject add(@RequestParam Map<String,String> data){
        String content=data.get("content");
        return addDialogService.add(content);
    }
}
