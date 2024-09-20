package com.atguigu.ssyx.acl.service.impl;

import com.atguigu.ssyx.acl.mapper.AdminMapper;
import com.atguigu.ssyx.acl.service.AdminService;
import com.atguigu.ssyx.model.acl.Admin;
import com.atguigu.ssyx.vo.acl.AdminQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 平台管理员接口实现类
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    /**
     * 获取管理员分页列表
     */
    @Override
    public IPage<Admin> selectPage(Page<Admin> pageParam, AdminQueryVo userQueryVo) {
        // 1.构造查询条件
        LambdaQueryWrapper<Admin> adminPageWrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(userQueryVo.getName())){
            adminPageWrapper.like(Admin::getName, userQueryVo.getName());
        }
        // 2.从数据库查询数据（分页）
        return baseMapper.selectPage(pageParam, adminPageWrapper);
    }
}
