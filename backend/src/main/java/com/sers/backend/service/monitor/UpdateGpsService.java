package com.sers.backend.service.monitor;

import com.alibaba.fastjson.JSONObject;

public interface UpdateGpsService {
    JSONObject update(Integer id,String name);
}
