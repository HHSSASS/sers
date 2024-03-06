package com.sers.backend.controller.notice;

import com.alibaba.fastjson.JSONObject;
import com.sers.backend.service.notice.GetNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GetNoticeController {
    @Autowired
    private GetNoticeService getNoticeService;

    @GetMapping("/api2/notice/get/")
    public JSONObject get(@RequestParam Map<String,String> data){
        Integer id=Integer.parseInt(data.get("id"));
        return getNoticeService.get(id);
    }
}
