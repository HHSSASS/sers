package com.sers.backend.controller.notice;

import com.alibaba.fastjson.JSONObject;
import com.sers.backend.service.notice.AddNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AddNoticeController {
    @Autowired
    private AddNoticeService addNoticeService;

    @PostMapping("/api/notice/add/")
    public JSONObject add(@RequestParam Map<String,String> data){
        String title=data.get("title");
        String content=data.get("content");
        return addNoticeService.add(title,content);
    }
}
