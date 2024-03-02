package com.sers.backend.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.sers.backend.service.admin.UpdatePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UpdatePermissionController {
    @Autowired
    private UpdatePermissionService addAdminService;

    @PostMapping("/api/admin/permission/update/")
    public JSONObject add(@RequestParam Map<String,String> data){
        Integer id=Integer.parseInt(data.get("id"));
        return addAdminService.add(id);
    }
}
