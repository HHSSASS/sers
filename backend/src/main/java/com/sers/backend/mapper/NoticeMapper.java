package com.sers.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sers.backend.pojo.Notice;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NoticeMapper extends BaseMapper<Notice> {
}
