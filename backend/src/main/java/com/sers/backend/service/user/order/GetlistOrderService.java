package com.sers.backend.service.user.order;

import com.alibaba.fastjson.JSONObject;

public interface GetlistOrderService {
    JSONObject getlist(Integer page,Integer condition);
}
