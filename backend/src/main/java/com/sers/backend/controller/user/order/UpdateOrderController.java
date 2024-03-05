package com.sers.backend.controller.user.order;

import com.alibaba.fastjson.JSONObject;
import com.sers.backend.service.user.order.UpdateOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UpdateOrderController {
    @Autowired
    private UpdateOrderService updateOrderService;

    @PostMapping("/api/order/update/")
    public JSONObject update(@RequestParam Map<String,String> data){
        Integer id=Integer.parseInt(data.get("id"));
        Integer status=Integer.parseInt(data.get("status"));
        return updateOrderService.update(id,status);
    }
}
