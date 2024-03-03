package com.sers.backend.controller.admin.shop;

import com.alibaba.fastjson.JSONObject;
import com.sers.backend.service.admin.shop.UpdateProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
public class UpdateProductController {
    @Autowired
    private UpdateProductService updateProductService;

    @PostMapping("/api/admin/shop/update/")
    public JSONObject update(@RequestParam("id") String id,
                             @RequestParam("photo") MultipartFile photo,
                             @RequestParam("name") String name,
                             @RequestParam("description") String description,
                             @RequestParam("price") String price){
        Integer id_int=Integer.parseInt(id);
        return updateProductService.update(id_int,photo,name,description,price);
    }
}
