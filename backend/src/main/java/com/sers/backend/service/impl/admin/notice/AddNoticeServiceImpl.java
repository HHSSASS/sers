package com.sers.backend.service.impl.admin.notice;

import com.alibaba.fastjson.JSONObject;
import com.sers.backend.mapper.NoticeMapper;
import com.sers.backend.pojo.Notice;
import com.sers.backend.pojo.User;
import com.sers.backend.service.admin.notice.AddNoticeService;
import com.sers.backend.utils.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AddNoticeServiceImpl implements AddNoticeService {
    @Autowired
    private NoticeMapper noticeMapper;

    @Override
    public JSONObject add(String title,String content) {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();
        JSONObject resp=new JSONObject();
        if(!user.getAdmin()){
            resp.put("message","无管理员权限");
            return resp;
        }
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
