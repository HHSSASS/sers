package com.sers.backend.service.admin.notice;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

public interface AddNoticeService {
    JSONObject add(String title,String content);
}
