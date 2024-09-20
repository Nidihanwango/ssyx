package com.atguigu.ssyx.search.service;

/**
 * 商品搜索服务
 */
public interface SkuService {

    // 商品上架
    void upperSku(Long skuId);
    // 商品下架
    void lowerSku(Long skuId);
}
