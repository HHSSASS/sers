package com.sers.backend.controller.monitor;

import com.alibaba.fastjson.JSONObject;
import com.sers.backend.service.monitor.AddGpsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AddGpsController {
    @Autowired
    private AddGpsService addGpsService;

    @PostMapping("/api/gps/add/")
    public JSONObject add(@RequestParam Map<String,String> data){
        String number=data.get("number");
        return addGpsService.add(number);
    }
}
