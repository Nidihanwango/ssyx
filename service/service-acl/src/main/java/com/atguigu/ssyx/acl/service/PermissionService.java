package com.atguigu.ssyx.acl.service;

import com.atguigu.ssyx.model.acl.Permission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 菜单管理接口
 */
public interface PermissionService extends IService<Permission> {
    /**
     * 获取所有菜单
     */
    List<Permission> queryAllMenu();

    /**
     * 根据id删除菜单及所有子菜单
     */
    boolean removeMenuById(Long id);

    /**
     * 根据角色id获取角色权限以及所有权限
     */
    List<Permission> toAssign(Long roleId);

    /**
     * 更新角色权限
     */
    void doAssign(Long roleId, Long[] permissionId);
}
