package com.sers.backend.controller.admin.dialog;

import com.alibaba.fastjson.JSONObject;
import com.sers.backend.service.admin.dialog.GetlistDialogUserService;
import com.sers.backend.service.admin.permission.GetlistUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetlistDialogUserController {
    @Autowired
    private GetlistDialogUserService getlistDialogUserService;

    @GetMapping("/api2/admin/dialog/user/getlist/")
    public JSONObject getlist(){
        return getlistDialogUserService.getlist();
    }
}
