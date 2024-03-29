package com.sers.backend.controller.admin.dialog;

import com.alibaba.fastjson.JSONObject;
import com.sers.backend.service.admin.dialog.AddAdminDialogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AddAdminDialogController {
    @Autowired
    private AddAdminDialogService addServiceDialogService;

    @PostMapping("/api2/admin/dialog/add/")
    public JSONObject add(@RequestParam Map<String,String> data){
        Integer id=Integer.parseInt(data.get("id"));
        String content=data.get("content");
        return addServiceDialogService.add(id,content);
    }
}
