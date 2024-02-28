package com.sers.backend.service.notice;

import com.alibaba.fastjson.JSONObject;

public interface GetlistNoticeService {
    JSONObject getlist(Integer page,Integer number);
}
