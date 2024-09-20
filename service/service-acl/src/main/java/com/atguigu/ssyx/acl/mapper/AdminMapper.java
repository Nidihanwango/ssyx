package com.atguigu.ssyx.acl.mapper;

import com.atguigu.ssyx.model.acl.Admin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 平台管理员mapper接口
 */
@Mapper
public interface AdminMapper extends BaseMapper<Admin> {
}
