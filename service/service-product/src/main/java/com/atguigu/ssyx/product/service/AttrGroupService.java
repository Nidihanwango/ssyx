package com.atguigu.ssyx.product.service;

import com.atguigu.ssyx.model.product.AttrGroup;
import com.atguigu.ssyx.vo.product.AttrGroupQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 平台属性分组服务接口
 */
public interface AttrGroupService extends IService<AttrGroup> {
    // 获取分页列表
    IPage<AttrGroup> getPage(Page<AttrGroup> pageParam, AttrGroupQueryVo attrGroupQueryVo);
    // 获取所有数据
    List<AttrGroup> findAllList();
}
