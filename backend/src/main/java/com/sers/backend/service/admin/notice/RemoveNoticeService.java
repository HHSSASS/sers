package com.sers.backend.service.admin.notice;

import com.alibaba.fastjson.JSONObject;

public interface RemoveNoticeService {
    JSONObject remove(Integer id);
}
