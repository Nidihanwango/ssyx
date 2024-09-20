package com.atguigu.ssyx.product.controller;

import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.product.Category;
import com.atguigu.ssyx.product.service.CategoryService;
import com.atguigu.ssyx.vo.product.CategoryQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 商品分类 前端控制器
 */
@RestController
//@CrossOrigin
@Api(tags = "商品分类管理")
@RequestMapping("/admin/product/category")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    @ApiOperation("获取商品分类分页列表")
    @GetMapping("/{curPage}/{limit}")
    public Result list(@PathVariable Integer curPage, @PathVariable Integer limit, CategoryQueryVo categoryQueryVo){
        Page<Category> pageParam = new Page<>(curPage, limit);
        IPage<Category> pageModel = categoryService.queryPage(pageParam, categoryQueryVo);
        return Result.ok(pageModel);
    }

    @ApiOperation("添加新商品分类")
    @PostMapping("/save")
    public Result save(@RequestBody Category category){
        categoryService.save(category);
        return Result.ok();
    }

    @ApiOperation("根据id获取商品分类")
    @GetMapping("/get/{id}")
    public Result getById(@PathVariable Long id){
        Category category = categoryService.getById(id);
        return Result.ok(category);
    }

    @ApiOperation("修改商品分类")
    @PutMapping("/update")
    public Result updateCategory(@RequestBody Category category){
        categoryService.updateById(category);
        return Result.ok();
    }

    @ApiOperation("根据id删除商品分类")
    @DeleteMapping("/remove/{id}")
    public Result remove(@PathVariable Long id){
        categoryService.removeById(id);
        return Result.ok();
    }

    @ApiOperation("根据id批量删除商品分类")
    @DeleteMapping("/batchRemove")
    public Result batchRemove(@RequestBody List<Long> ids){
        categoryService.removeByIds(ids);
        return Result.ok();
    }

    @ApiOperation(value = "获取全部商品分类")
    @GetMapping("findAllList")
    public Result findAllList() {
        List<Category> result = categoryService.findAllList();
        return Result.ok(result);
    }
}
