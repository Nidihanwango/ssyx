package com.atguigu.ssyx.product.controller;

import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.product.SkuInfo;
import com.atguigu.ssyx.product.service.SkuInfoService;
import com.atguigu.ssyx.vo.product.SkuInfoQueryVo;
import com.atguigu.ssyx.vo.product.SkuInfoVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * sku信息 前端控制器
 */
@RestController
@CrossOrigin
@Api(tags = "sku信息管理")
@RequestMapping("/admin/product/skuInfo")
public class SkuInfoController {
    @Resource
    private SkuInfoService skuInfoService;

    @ApiOperation("分页查询")
    @GetMapping("/{curPage}/{size}")
    public Result getPage(@PathVariable Long curPage, @PathVariable Long size, SkuInfoQueryVo vo) {
        Page<SkuInfo> pageParam = new Page<>(curPage, size);
        IPage<SkuInfo> pageModel = skuInfoService.getPage(pageParam, vo);
        return Result.ok(pageModel);
    }

    @ApiOperation("新增")
    @PostMapping("/save")
    public Result save(@RequestBody SkuInfoVo skuInfoVo) {
        skuInfoService.saveSkuInfoVo(skuInfoVo);
        return Result.ok();
    }

    @ApiOperation("获取商品信息")
    @GetMapping("/get/{id}")
    public Result getById(@PathVariable Long id) {
        SkuInfoVo skuInfoVo = skuInfoService.getSkuInfoVoById(id);
        return Result.ok(skuInfoVo);
    }

    @ApiOperation("修改")
    @PutMapping("/update")
    public Result update(@RequestBody SkuInfoVo skuInfoVo) {
        skuInfoService.updateSkuInfoVo(skuInfoVo);
        return Result.ok();
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id) {
        skuInfoService.removeById(id);
        return Result.ok();
    }

    @ApiOperation(value = "根据id列表删除")
    @DeleteMapping("batchRemove")
    public Result batchRemove(@RequestBody List<Long> idList) {
        skuInfoService.removeByIds(idList);
        return Result.ok();
    }

    @ApiOperation("审核商品")
    @GetMapping("/check/{skuId}/{status}")
    public Result check(@PathVariable Long skuId, @PathVariable Integer status) {
        skuInfoService.check(skuId, status);
        return Result.ok();
    }

    @ApiOperation("商品上下架")
    @GetMapping("/publish/{skuId}/{status}")
    public Result publish(@PathVariable Long skuId, @PathVariable Integer status) {
        skuInfoService.publish(skuId, status);
        return Result.ok();
    }

    @ApiOperation("新人专享")
    @GetMapping("/isNewPerson/{skuId}/{status}")
    public Result updateIsNewPersonStatus(@PathVariable Long skuId, @PathVariable Integer status) {
        skuInfoService.updateIsNewPersonStatus(skuId, status);
        return Result.ok();
    }

}
