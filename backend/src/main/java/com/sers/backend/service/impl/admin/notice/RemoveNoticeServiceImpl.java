package com.sers.backend.service.impl.admin.notice;

import com.alibaba.fastjson.JSONObject;
import com.sers.backend.mapper.NoticeMapper;
import com.sers.backend.pojo.User;
import com.sers.backend.service.admin.notice.RemoveNoticeService;
import com.sers.backend.utils.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class RemoveNoticeServiceImpl implements RemoveNoticeService {
    @Autowired
    private NoticeMapper noticeMapper;

    @Override
    public JSONObject remove(Integer id) {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();
        JSONObject resp=new JSONObject();
        if(!user.getAdmin()){
            resp.put("message","无管理员权限");
            return resp;
        }
        noticeMapper.deleteById(id);
        resp.put("message","successful");
        return resp;
    }
}
