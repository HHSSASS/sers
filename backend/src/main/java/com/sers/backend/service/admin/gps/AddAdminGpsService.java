package com.sers.backend.service.admin.gps;

import com.alibaba.fastjson.JSONObject;

public interface AddAdminGpsService {
    JSONObject add(String number,String type);
}
