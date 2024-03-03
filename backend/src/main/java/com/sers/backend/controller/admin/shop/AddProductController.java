package com.sers.backend.controller.admin.shop;

import com.alibaba.fastjson.JSONObject;
import com.sers.backend.service.admin.shop.AddProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
public class AddProductController {
    @Autowired
    private AddProductService addProductService;

    @PostMapping("/api/admin/shop/add/")
    public JSONObject add(@RequestParam("photo") MultipartFile photo,
                          @RequestParam("name") String name,
                          @RequestParam("description") String description,
                          @RequestParam("price") String price){
        return addProductService.add(photo,name,description,price);
    }
}
