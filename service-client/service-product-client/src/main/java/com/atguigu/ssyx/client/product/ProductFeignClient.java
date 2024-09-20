package com.atguigu.ssyx.client.product;

import com.atguigu.ssyx.model.product.Category;
import com.atguigu.ssyx.model.product.SkuInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "service-product")
public interface ProductFeignClient {
    @GetMapping("/api/product/inner/getCategory/{categoryId}")
    Category getCategoryById(@PathVariable("categoryId") Long categoryId);

    // 批量获取分类信息
    @PostMapping("/api/product/inner/getCategoryList")
    List<Category> getCategoryList(@RequestBody List<Long> categoryIdList);

    @GetMapping("/api/product/inner/getSkuInfo/{skuId}")
    SkuInfo getSkuInfoById(@PathVariable("skuId") Long skuId);

    // 批量获取sku信息
    @PostMapping("/api/product/inner/getSkuInfoList")
    List<SkuInfo> getSkuInfoList(@RequestBody List<Long> skuIds);

    // 根据关键字匹配sku信息
    @GetMapping("/api/product/inner/getSkuByKeyword/{keyword}")
    List<SkuInfo> getSkuByKeyword(@PathVariable("keyword") String keyword);
}
