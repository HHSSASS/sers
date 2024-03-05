package com.sers.backend.service.admin.product;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.multipart.MultipartFile;

public interface AddProductService {
    JSONObject add(MultipartFile photo,String name,String description,String price);
}
