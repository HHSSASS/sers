package com.sers.backend.controller.admin.gps;

import com.alibaba.fastjson.JSONObject;
import com.sers.backend.service.admin.gps.AddAdminGpsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AddAdminGpsController {
    @Autowired
    private AddAdminGpsService addAdminGpsService;

    @PostMapping("/api/admin/gps/add/")
    public JSONObject add(@RequestParam Map<String,String> data){
        String number=data.get("number");
        String type=data.get("type");
        return addAdminGpsService.add(number,type);
    }
}
