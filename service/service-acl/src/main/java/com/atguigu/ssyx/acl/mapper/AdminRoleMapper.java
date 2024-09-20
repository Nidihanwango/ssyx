package com.atguigu.ssyx.acl.mapper;

import com.atguigu.ssyx.model.acl.AdminRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 管理员-角色 关系表mapper
 */
@Mapper
public interface AdminRoleMapper extends BaseMapper<AdminRole> {
}
