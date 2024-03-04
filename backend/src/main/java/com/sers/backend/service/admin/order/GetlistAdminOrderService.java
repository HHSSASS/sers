package com.sers.backend.service.admin.order;

import com.alibaba.fastjson.JSONObject;

public interface GetlistAdminOrderService {
    JSONObject getlist(Integer page,String condition);
}
