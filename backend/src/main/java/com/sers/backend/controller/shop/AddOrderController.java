package com.sers.backend.controller.shop;

import com.alibaba.fastjson.JSONObject;
import com.sers.backend.service.shop.AddOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AddOrderController {
    @Autowired
    private AddOrderService addOrderService;

    @PostMapping("/api2/order/add/")
    public JSONObject add(@RequestParam Map<String,String> data){
        Integer product_id=Integer.parseInt(data.get("id"));
        String number=data.get("number");
        String phone=data.get("phone");
        String address=data.get("address");
        Integer method=Integer.parseInt(data.get("method"));
        return addOrderService.add(product_id,number,phone,address,method);
    }
}
