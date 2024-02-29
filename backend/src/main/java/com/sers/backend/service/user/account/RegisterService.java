package com.sers.backend.service.user.account;

import com.alibaba.fastjson.JSONObject;


public interface RegisterService {
    JSONObject register(String username, String password, String confirmPassword);
}
