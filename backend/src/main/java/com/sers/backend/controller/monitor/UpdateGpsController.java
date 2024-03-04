package com.sers.backend.controller.monitor;

import com.alibaba.fastjson.JSONObject;
import com.sers.backend.service.monitor.UpdateGpsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UpdateGpsController {
    @Autowired
    private UpdateGpsService updateGpsService;

    @PostMapping("/api/monitor/update/")
    public JSONObject update(@RequestParam Map<String,String> data){
        Integer id=Integer.parseInt(data.get("id"));
        String name=data.get("name");
        return updateGpsService.update(id,name);
    }
}
