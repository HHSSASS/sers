package com.sers.backend.controller.admin.product;

import com.alibaba.fastjson.JSONObject;
import com.sers.backend.service.admin.product.RemoveProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RemoveProductController {
    @Autowired
    private RemoveProductService removeProductService;

    @PostMapping("/api2/admin/product/remove/")
    public JSONObject remove(@RequestParam Map<String,String> data){
        Integer id=Integer.parseInt(data.get("id"));
        return removeProductService.remove(id);
    }
}
