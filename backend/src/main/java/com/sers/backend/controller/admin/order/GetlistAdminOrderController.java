package com.sers.backend.controller.admin.order;

import com.alibaba.fastjson.JSONObject;
import com.sers.backend.service.admin.order.GetlistAdminOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GetlistAdminOrderController {
    @Autowired
    private GetlistAdminOrderService getlistAdminOrderService;

    @GetMapping("/api/admin/order/getlist/")
    public JSONObject getlist(@RequestParam Map<String,String> data){
        Integer page=Integer.parseInt(data.get("page"));
        Integer condition=Integer.parseInt(data.get("condition"));
        return getlistAdminOrderService.getlist(page,condition);
    }
}
