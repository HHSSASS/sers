package com.sers.backend.service.admin.notice;

import com.alibaba.fastjson.JSONObject;

public interface UpdateNoticeService {
    JSONObject update(Integer id,String title,String content);
}
