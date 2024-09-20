package com.atguigu.ssyx.activity.controller;

import com.atguigu.ssyx.activity.service.ActivityInfoService;
import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.activity.ActivityInfo;
import com.atguigu.ssyx.model.product.SkuInfo;
import com.atguigu.ssyx.vo.activity.ActivityRuleVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/admin/activity/activityInfo")
@Api(tags = "营销活动管理")
public class ActivityInfoController {

    @Resource
    private ActivityInfoService activityInfoService;

    @ApiOperation("分页查询")
    @GetMapping("/{page}/{size}")
    public Result page(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        IPage<ActivityInfo> pageParam = new Page<>(page, size);
        IPage<ActivityInfo> pageModel = activityInfoService.getActivityPage(pageParam);
        return Result.ok(pageModel);
    }

    @ApiOperation("根据id获取")
    @GetMapping("/get/{id}")
    public Result getById(@PathVariable("id") Long id) {
        ActivityInfo activityInfo = activityInfoService.getById(id);
        activityInfo.setActivityTypeString(activityInfo.getActivityType().getComment());
        return Result.ok(activityInfo);
    }

    @ApiOperation("新增")
    @PostMapping("/save")
    public Result save(@RequestBody ActivityInfo activityInfo) {
        activityInfo.setCreateTime(new Date());
        activityInfoService.save(activityInfo);
        return Result.ok();
    }


    @ApiOperation("修改")
    @PutMapping("/update")
    public Result updateById(@RequestBody ActivityInfo activityInfo){
        activityInfoService.updateById(activityInfo);
        return Result.ok();
    }

    @ApiOperation("删除")
    @DeleteMapping("/remove/{id}")
    public Result removeById(@PathVariable("id") Long id) {
        activityInfoService.removeById(id);
        return Result.ok();
    }

    @ApiOperation("批量删除")
    @DeleteMapping("/batchRemove")
    public Result batchRemove(@RequestBody List<Long> idList) {
        activityInfoService.removeByIds(idList);
        return Result.ok();
    }

    @ApiOperation("获取活动规则")
    @GetMapping("/findActivityRuleList/{id}")
    public Result findActivityRuleList(@PathVariable("id") Long id){
        Map<String, Object> map = activityInfoService.findActivityRuleList(id);
        return Result.ok(map);
    }

    @ApiOperation("保存活动规则")
    @PostMapping("/saveActivityRule")
    public Result saveActivityRule(@RequestBody ActivityRuleVo activityRuleVo){
        activityInfoService.saveActivityRule(activityRuleVo);
        return Result.ok();
    }

    @ApiOperation("根据关键字匹配sku信息")
    @GetMapping("/findSkuInfoByKeyword/{keyword}")
    public Result findSkuInfoByKeyword(@PathVariable("keyword") String keyword) {
        List<SkuInfo> skuInfoList = activityInfoService.findSkuInfoByKeyword(keyword);
        return Result.ok(skuInfoList);
    }

}
