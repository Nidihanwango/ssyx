package com.atguigu.ssyx.product.controller;

import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.product.Attr;
import com.atguigu.ssyx.product.service.AttrService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 平台属性 前端控制器
 */
@RestController
@CrossOrigin
@Api(tags = "平台属性管理")
@RequestMapping("/admin/product/attr")
public class AttrController {

    @Resource
    private AttrService attrService;

    @ApiOperation("根据分组id获取")
    @GetMapping("/{groupId}")
    public Result getByGroupId(@PathVariable Long groupId){
        List<Attr> result = attrService.getByGroupId(groupId);
        return Result.ok(result);
    }

    @ApiOperation("新增")
    @PostMapping("/save")
    public Result save(@RequestBody Attr attr){
        attrService.save(attr);
        return Result.ok();
    }

    @ApiOperation("删除")
    @DeleteMapping("/remove/{id}")
    public Result remove(@PathVariable Long id){
        attrService.removeById(id);
        return Result.ok();
    }

    @ApiOperation("批量删除")
    @DeleteMapping("/batchRemove")
    public Result batchRemove(@RequestBody List<Long> ids) {
        attrService.removeByIds(ids);
        return Result.ok();
    }

    @ApiOperation("根据属性id获取")
    @GetMapping("/get/{id}")
    public Result getById(@PathVariable Long id) {
        return Result.ok(attrService.getById(id));
    }

    @ApiOperation("更新")
    @PutMapping("/update")
    public Result update(@RequestBody Attr attr) {
        attrService.updateById(attr);
        return Result.ok();
    }
}














































