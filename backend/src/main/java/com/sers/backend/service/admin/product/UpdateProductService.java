package com.sers.backend.service.admin.product;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.multipart.MultipartFile;

public interface UpdateProductService {
    JSONObject update(Integer id, MultipartFile photo,String name,String description,String price);
}
