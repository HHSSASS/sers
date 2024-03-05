package com.sers.backend.controller.user.order;

import com.alibaba.fastjson.JSONObject;
import com.sers.backend.service.user.order.GetlistOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GetlistOrderController {
    @Autowired
    private GetlistOrderService getlistOrderService;

    @GetMapping("/api/order/getlist/")
    public JSONObject getlist(@RequestParam Map<String,String> data){
        Integer page=Integer.parseInt(data.get("page"));
        Integer condition=Integer.parseInt(data.get("condition"));
        return getlistOrderService.getlist(page,condition);
    }
}
