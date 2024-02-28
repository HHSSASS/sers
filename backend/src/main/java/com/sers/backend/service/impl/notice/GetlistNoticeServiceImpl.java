package com.sers.backend.service.impl.notice;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sers.backend.mapper.NoticeMapper;
import com.sers.backend.pojo.Notice;
import com.sers.backend.service.notice.GetlistNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetlistNoticeServiceImpl implements GetlistNoticeService {
    @Autowired
    private NoticeMapper noticeMapper;

    @Override
    public JSONObject getlist(Integer page, Integer number) {
        IPage<Notice> noticeIPage=new Page<>(page,number);
        QueryWrapper<Notice> queryWrapper=new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        List<Notice> notices=noticeMapper.selectPage(noticeIPage,queryWrapper).getRecords();
        JSONObject resp=new JSONObject();
        resp.put("message","successful");
        resp.put("notices",notices);
        resp.put("notices_count",noticeMapper.selectCount(null));
        return resp;
    }
}
