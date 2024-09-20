package com.atguigu.ssyx.product.service;

import com.atguigu.ssyx.model.product.Category;
import com.atguigu.ssyx.vo.product.CategoryQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 商品分类服务接口
 */
public interface CategoryService extends IService<Category> {
    // 获取商品分类分页列表
    IPage<Category> queryPage(Page<Category> pageParam, CategoryQueryVo categoryQueryVo);
    // 获取全部商品分类
    List<Category> findAllList();
}
