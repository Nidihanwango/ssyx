package com.atguigu.ssyx.product.api;

import com.atguigu.ssyx.model.product.Category;
import com.atguigu.ssyx.model.product.SkuInfo;
import com.atguigu.ssyx.product.service.CategoryService;
import com.atguigu.ssyx.product.service.SkuInfoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/product/inner")
public class ProductInnerController {

    @Resource
    private CategoryService categoryService;

    @Resource
    private SkuInfoService skuInfoService;

    @ApiOperation("根据分类id获取分类信息")
    @GetMapping("/getCategory/{categoryId}")
    public Category getCategoryById(@PathVariable("categoryId") Long categoryId) {
        return categoryService.getById(categoryId);
    }
    @ApiOperation("批量获取分类信息")
    @PostMapping("/getCategoryList")
    public List<Category> getCategoryList(@RequestBody List<Long> categoryIdList) {
        return categoryService.listByIds(categoryIdList);
    }

    @ApiOperation("根据skuId获取sku信息")
    @GetMapping("/getSkuInfo/{skuId}")
    public SkuInfo getSkuInfoById(@PathVariable("skuId") Long skuId) {
        return skuInfoService.getById(skuId);
    }

    @ApiOperation("批量获取sku信息")
    @PostMapping("/getSkuInfoList")
    public List<SkuInfo> getSkuInfoList(@RequestBody List<Long> skuIds) {
        return skuInfoService.listByIds(skuIds);
    }

    @ApiOperation("根据关键字匹配sku信息")
    @GetMapping("/getSkuByKeyword/{keyword}")
    public List<SkuInfo> getSkuByKeyword(@PathVariable("keyword") String keyword) {
        return skuInfoService.getSkuByKeyword(keyword);
    }
}
