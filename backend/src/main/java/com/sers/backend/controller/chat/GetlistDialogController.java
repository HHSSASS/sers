package com.sers.backend.controller.chat;

import com.alibaba.fastjson.JSONObject;
import com.sers.backend.service.chat.GetlistDialogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetlistDialogController {
    @Autowired
    private GetlistDialogService getlistDialogService;

    @GetMapping("/api/chat/getlist/")
    public JSONObject getlist(){
        return getlistDialogService.getlist();
    }
}
