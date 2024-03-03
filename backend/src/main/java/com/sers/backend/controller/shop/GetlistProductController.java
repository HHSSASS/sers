package com.sers.backend.controller.shop;

import com.alibaba.fastjson.JSONObject;
import com.sers.backend.service.shop.GetlistProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GetlistProductController {
    @Autowired
    private GetlistProductService getlistProductService;

    @GetMapping("/api/shop/getlist/")
    public JSONObject getlist(@RequestParam Map<String,String> data){
        Integer page=Integer.parseInt(data.get("page"));
        return getlistProductService.getlist(page);
    }
}
