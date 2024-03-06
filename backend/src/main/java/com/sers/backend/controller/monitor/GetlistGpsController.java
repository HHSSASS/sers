package com.sers.backend.controller.monitor;

import com.alibaba.fastjson.JSONObject;
import com.sers.backend.service.monitor.GetlistGpsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GetlistGpsController {
    @Autowired
    private GetlistGpsService getlistGpsService;

    @GetMapping("/api2/gps/getlist/")
    public JSONObject getlist(@RequestParam Map<String,String> data){
        Integer page=Integer.parseInt(data.get("page"));
        return getlistGpsService.getlist(page);
    }
}
