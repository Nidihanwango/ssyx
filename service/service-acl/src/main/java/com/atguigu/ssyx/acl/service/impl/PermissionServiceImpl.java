package com.atguigu.ssyx.acl.service.impl;

import com.atguigu.ssyx.acl.helper.PermissionHelper;
import com.atguigu.ssyx.acl.mapper.PermissionMapper;
import com.atguigu.ssyx.acl.service.PermissionService;
import com.atguigu.ssyx.acl.service.RolePermissionService;
import com.atguigu.ssyx.model.acl.Permission;
import com.atguigu.ssyx.model.acl.RolePermission;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单管理实现类
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Resource
    private RolePermissionService rolePermissionService;

    /**
     * 获取所有菜单
     */
    @Override
    public List<Permission> queryAllMenu() {
        List<Permission> allPermission = baseMapper.selectList(new QueryWrapper<Permission>().orderByAsc("CAST(id AS SIGNED)"));
        return PermissionHelper.build(allPermission);
    }

    /**
     * 根据id删除菜单及所有子菜单
     */
    @Override
    public boolean removeMenuById(Long id) {
        return false;
    }

    /**
     * 根据角色id获取角色权限以及所有权限
     */
    @Override
    public List<Permission> toAssign(Long roleId) {
        // 1.获取所有权限
        List<Permission> allPermission = this.list(new QueryWrapper<Permission>().orderByAsc("CAST(id AS SIGNED)"));
        // 2.获取角色关联的权限id
        List<RolePermission> rolePermissions = rolePermissionService.list(new LambdaQueryWrapper<RolePermission>().eq(RolePermission::getRoleId, roleId).select(RolePermission::getPermissionId));
        List<Long> rolePermissionIds = rolePermissions.stream().map(RolePermission::getPermissionId).collect(Collectors.toList());
        // 3.在所有权限中选中角色权限
        for (Permission permission : allPermission) {
            if (rolePermissionIds.contains(permission.getId())){
                permission.setSelect(true);
            }
        }
        // 4.将数据转化成树形结构
        // 5.返回数据
        List<Permission> tree = PermissionHelper.build(allPermission);
        return tree;
    }
    /**
     * 更新角色权限
     */
    @Transactional
    @Override
    public void doAssign(Long roleId, Long[] permissionId) {
        // 1.删除原数据
        rolePermissionService.remove(new LambdaQueryWrapper<RolePermission>().eq(RolePermission::getRoleId, roleId));
        // 2.保存新数据
        List<RolePermission> rolePermissionList = new ArrayList<>();
        for (Long id : permissionId) {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(id);
            rolePermissionList.add(rolePermission);
        }
        rolePermissionService.saveBatch(rolePermissionList);
    }

}
