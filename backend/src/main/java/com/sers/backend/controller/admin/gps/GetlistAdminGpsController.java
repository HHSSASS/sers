package com.sers.backend.controller.admin.gps;

import com.alibaba.fastjson.JSONObject;
import com.sers.backend.service.admin.gps.GetlistAdminGpsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GetlistAdminGpsController {
    @Autowired
    private GetlistAdminGpsService getlistAdminGpsService;

    @GetMapping("/api/admin/gps/getlist/")
    public JSONObject getlist(@RequestParam Map<String,String> data){
        Integer page=Integer.parseInt(data.get("page"));
        return getlistAdminGpsService.getlist(page);
    }
}
