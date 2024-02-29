package com.sers.backend.service.user.account;

import com.alibaba.fastjson.JSONObject;


public interface LoginService {
    JSONObject login(String username, String password);
}
