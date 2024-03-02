package com.sers.backend.controller.admin.notice;

import com.alibaba.fastjson.JSONObject;
import com.sers.backend.service.admin.notice.RemoveNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RemoveNoticeController {
    @Autowired
    private RemoveNoticeService removeNoticeService;

    @PostMapping("/api/admin/notice/remove/")
    public JSONObject remove(@RequestParam Map<String,String> data){
        Integer id=Integer.parseInt(data.get("id"));
        return removeNoticeService.remove(id);
    }
}
