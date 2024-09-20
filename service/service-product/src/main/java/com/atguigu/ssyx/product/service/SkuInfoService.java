package com.atguigu.ssyx.product.service;

import com.atguigu.ssyx.model.product.SkuInfo;
import com.atguigu.ssyx.vo.product.SkuInfoQueryVo;
import com.atguigu.ssyx.vo.product.SkuInfoVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * sku信息服务接口
 */
public interface SkuInfoService extends IService<SkuInfo> {
    // 分页查询
    IPage<SkuInfo> getPage(Page<SkuInfo> pageParam, SkuInfoQueryVo vo);
    // 添加商品
    void saveSkuInfoVo(SkuInfoVo skuInfoVo);
    // 获取商品信息
    SkuInfoVo getSkuInfoVoById(Long id);
    // 修改商品
    void updateSkuInfoVo(SkuInfoVo skuInfoVo);
    // 审核商品
    void check(Long skuId, Integer status);
    // 商品上下架
    void publish(Long skuId, Integer status);
    // 新人专享
    void updateIsNewPersonStatus(Long skuId, Integer status);
    // 根据关键字获取sku
    List<SkuInfo> getSkuByKeyword(String keyword);
}
