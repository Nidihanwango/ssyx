package com.atguigu.ssyx.acl.mapper;

import com.atguigu.ssyx.model.acl.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 菜单Mapper接口
 */
@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {
}
