package com.sers.backend.service.chat;

import com.alibaba.fastjson.JSONObject;

public interface AddDialogService{
    JSONObject add(String content);
}
