package com.sers.backend.controller.notice;

import com.alibaba.fastjson.JSONObject;
import com.sers.backend.service.notice.GetlistNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GetlistNoticeController {
    @Autowired
    private GetlistNoticeService getNoticeService;

    @GetMapping("/api/notice/getlist/")
    public JSONObject get(@RequestParam Map<String,String> data){
        Integer page=Integer.parseInt(data.get("page"));
        Integer number=Integer.parseInt(data.get("number"));
        return getNoticeService.getlist(page,number);
    }
}
