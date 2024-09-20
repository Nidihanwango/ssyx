package com.atguigu.ssyx.product.controller;

import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.product.AttrGroup;
import com.atguigu.ssyx.product.service.AttrGroupService;
import com.atguigu.ssyx.vo.product.AttrGroupQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 平台属性分组 前端控制器
 */
@RestController
@CrossOrigin
@Api(tags = "平台属性分组管理")
@RequestMapping("/admin/product/attrGroup")
public class AttrGroupController {

    @Resource
    private AttrGroupService attrGroupService;

    @ApiOperation("获取分页列表")
    @GetMapping("/{curPage}/{size}")
    public Result getPage(@PathVariable Long curPage, @PathVariable Long size, AttrGroupQueryVo attrGroupQueryVo){
        Page<AttrGroup> pageParam = new Page<>(curPage, size);
        IPage<AttrGroup> pageModel = attrGroupService.getPage(pageParam, attrGroupQueryVo);
        return Result.ok(pageModel);
    }

    @ApiOperation("新增")
    @PostMapping("/save")
    public Result save(@RequestBody AttrGroup attrGroup) {
        attrGroupService.save(attrGroup);
        return Result.ok();
    }

    @ApiOperation("根据id删除")
    @DeleteMapping("/remove/{id}")
    public Result removeById(@PathVariable Long id) {
        attrGroupService.removeById(id);
        return Result.ok();
    }

    @ApiOperation("根据id列表删除")
    @DeleteMapping("/batchRemove")
    public Result batchRemove(@RequestBody List<Long> ids) {
        attrGroupService.removeByIds(ids);
        return Result.ok();
    }

    @ApiOperation("根据id获取")
    @GetMapping("/get/{id}")
    public Result getById(@PathVariable Long id){
        AttrGroup attrGroup = attrGroupService.getById(id);
        return Result.ok(attrGroup);
    }

    @ApiOperation("更新")
    @PutMapping("/update")
    public Result update(@RequestBody AttrGroup attrGroup) {
        attrGroupService.updateById(attrGroup);
        return Result.ok();
    }

    @ApiOperation("获取所有")
    @GetMapping("/findAllList")
    public Result findAllList() {
        List<AttrGroup> result = attrGroupService.findAllList();
        return Result.ok(result);
    }
}
