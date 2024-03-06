package com.sers.backend.service.shop;

import com.alibaba.fastjson.JSONObject;

public interface AddOrderService {
    JSONObject add(Integer product_id,String number,String phone,String address,Integer method);
}
