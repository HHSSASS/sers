package com.sers.backend.service.user.order;

import com.alibaba.fastjson.JSONObject;

public interface UpdateOrderService {
    JSONObject update(Integer id,Integer status);
}
