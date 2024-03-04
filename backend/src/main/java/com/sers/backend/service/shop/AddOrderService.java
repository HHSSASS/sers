package com.sers.backend.service.shop;

import com.alibaba.fastjson.JSONObject;

public interface AddOrderService {
    JSONObject add(Integer product_id,String address,String number,Integer method);
}
