package com.sers.backend.service.impl.notice;

import com.alibaba.fastjson.JSONObject;
import com.sers.backend.mapper.NoticeMapper;
import com.sers.backend.pojo.Notice;
import com.sers.backend.service.notice.GetNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetNoticeServiceImpl implements GetNoticeService {
    @Autowired
    private NoticeMapper noticeMapper;

    @Override
    public JSONObject get(Integer id) {
        Notice notice=noticeMapper.selectById(id);
        JSONObject resp=new JSONObject();
        resp.put("message","successful");
        resp.put("notice",notice);
        return resp;
    }
}
