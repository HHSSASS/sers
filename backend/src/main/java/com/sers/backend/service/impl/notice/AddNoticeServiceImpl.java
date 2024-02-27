package com.sers.backend.service.impl.notice;

import com.alibaba.fastjson.JSONObject;
import com.sers.backend.mapper.NoticeMapper;
import com.sers.backend.pojo.Notice;
import com.sers.backend.service.notice.AddNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AddNoticeServiceImpl implements AddNoticeService {
    @Autowired
    private NoticeMapper noticeMapper;

    @Override
    public JSONObject add(String title,String content) {
        JSONObject resp=new JSONObject();
        if(title==null||title.length()==0){
            resp.put("message","标题不能为空");
            return resp;
        }
        if(title.length()>100){
            resp.put("message","标题长度大于超过100");
            return resp;
        }
        if(content==null||content.length()==0){
            resp.put("message","内容不能为空");
            return resp;
        }
        if(content.length()>1000){
            resp.put("message","内容长度大于超过1000");
            return resp;
        }
        Date now=new Date();
        Notice notice=new Notice(null,title,content,now);
        noticeMapper.insert(notice);
        resp.put("message","successful");
        return resp;
    }
}
