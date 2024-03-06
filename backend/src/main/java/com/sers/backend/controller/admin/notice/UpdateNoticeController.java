package com.sers.backend.controller.admin.notice;

import com.alibaba.fastjson.JSONObject;
import com.sers.backend.service.admin.notice.UpdateNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UpdateNoticeController {
    @Autowired
    private UpdateNoticeService updateNoticeService;

    @PostMapping("/api2/admin/notice/update/")
    public JSONObject update(@RequestParam Map<String,String> data){
        Integer id=Integer.parseInt(data.get("id"));
        String title=data.get("title");
        String content=data.get("content");
        return updateNoticeService.update(id,title,content);
    }
}
