package com.atguigu.ssyx.acl.service.impl;

import com.atguigu.ssyx.acl.mapper.RoleMapper;
import com.atguigu.ssyx.acl.service.AdminRoleService;
import com.atguigu.ssyx.acl.service.RoleService;
import com.atguigu.ssyx.model.acl.AdminRole;
import com.atguigu.ssyx.model.acl.Role;
import com.atguigu.ssyx.vo.acl.RoleQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 角色管理接口实现类
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Resource
    private AdminRoleService adminRoleService;

    @Override
    public IPage<Role> selectPage(Page<Role> pageParam, RoleQueryVo roleQueryVo) {
        //获取条件值：角色名称
        String roleName = roleQueryVo.getRoleName();
        //创建条件构造器对象
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        //判断条件值是否为空
        if(!StringUtils.isEmpty(roleName)) {
            //封装条件
            wrapper.like(Role::getRoleName,roleName);
        }
        //调用mapper方法实现条件分页查询
        return baseMapper.selectPage(pageParam, wrapper);
    }

    /**
     * 根据用户获取角色数据
     */
    @Override
    public Map<String, Object> findRoleByAdminId(Long adminId) {
        Map<String, Object> map = new HashMap<>();
        // 1.获取所有角色
        List<Role> allRoleList = baseMapper.selectList(null);
        map.put("allRolesList", allRoleList);
        // 2.获取用户角色
        LambdaQueryWrapper<AdminRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AdminRole::getAdminId, adminId);
        List<AdminRole> adminRoleList = adminRoleService.list(wrapper);
        List<Long> assignRoleIds = adminRoleList.stream().map(AdminRole::getRoleId).collect(Collectors.toList());
        List<Role> assignRoles = allRoleList.stream().filter(role -> assignRoleIds.contains(role.getId())).collect(Collectors.toList());
        map.put("assignRoles", assignRoles);
        return map;
    }

    /**
     * 根据用户分配角色
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAdminRoleRealtionShip(Long adminId, Long[] roleIds) {
        // 1.删除原有数据
        adminRoleService.remove(new LambdaQueryWrapper<AdminRole>().eq(AdminRole::getAdminId, adminId));
        // 2.保存新数据
        List<AdminRole> newAdminRoleList = new ArrayList<>();
        for (Long id : roleIds) {
            if (id == null) {
                continue;
            }
            AdminRole adminRole = new AdminRole();
            adminRole.setAdminId(adminId);
            adminRole.setRoleId(id);
            newAdminRoleList.add(adminRole);
        }
        adminRoleService.saveBatch(newAdminRoleList);
    }
}
