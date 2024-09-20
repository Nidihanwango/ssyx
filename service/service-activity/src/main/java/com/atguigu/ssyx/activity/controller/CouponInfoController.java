package com.atguigu.ssyx.activity.controller;

import com.atguigu.ssyx.activity.service.CouponInfoService;
import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.activity.CouponInfo;
import com.atguigu.ssyx.vo.activity.CouponRuleVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/admin/activity/couponInfo")
@Api(tags = "优惠券管理")
public class CouponInfoController {

    @Resource
    private CouponInfoService couponInfoService;

    //    url: `${api_name}/${page}/${limit}`,
    //    method: 'get'
    @ApiOperation("分页查询")
    @GetMapping("{page}/{limit}")
    public Result page(@PathVariable("page") Long page, @PathVariable("limit") Long limit) {
        Page<CouponInfo> pageParam = new Page<>(page, limit);
        IPage<CouponInfo> pageModel = couponInfoService.selectPage(pageParam);
        return Result.ok(pageModel);
    }

    //    url: `${api_name}/get/${id}`,
    //    method: 'get'
    @ApiOperation("根据id查询")
    @GetMapping("get/{id}")
    public Result getById(@PathVariable("id") Long id) {
        CouponInfo couponInfo = couponInfoService.getCouponInfoById(id);
        couponInfo.setCouponTypeString(couponInfo.getCouponType().getComment());
        if (couponInfo.getRangeType() != null) {
            couponInfo.setRangeTypeString(couponInfo.getRangeType().getComment());
        }
        return Result.ok(couponInfo);
    }

    //    url: `${api_name}/save`,
    //    method: 'post',
    //    data: role
    @ApiOperation("新增")
    @PostMapping("save")
    public Result save(@RequestBody CouponInfo couponInfo) {
        couponInfoService.save(couponInfo);
        return Result.ok();
    }

    //    url: `${api_name}/update`,
    //    method: 'put',
    //    data: role
    @ApiOperation("更新")
    @PutMapping("update")
    public Result update(@RequestBody CouponInfo couponInfo) {
        couponInfoService.updateById(couponInfo);
        return Result.ok();
    }

    //    url: `${api_name}/remove/${id}`,
    //    method: 'delete'
    @ApiOperation("根据id删除")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable("id") Long id) {
        couponInfoService.removeById(id);
        return Result.ok();
    }

    //    url: `${api_name}/batchRemove`,
    //    method: 'delete',
    //    data: idList
    @ApiOperation("批量删除")
    @DeleteMapping("batchRemove")
    public Result batchRemove(@RequestBody List<Long> idList) {
        couponInfoService.removeByIds(idList);
        return Result.ok();
    }

    //    url: `${api_name}/findCouponRuleList/${id}`,
    //    method: 'get'
    @ApiOperation("根据id获取优惠券规则")
    @GetMapping("findCouponRuleList/{id}")
    public Result findCouponRuleList(@PathVariable("id") Long id) {
        Map<String, Object> map = couponInfoService.findCouponRuleList(id);
        return Result.ok(map);
    }
//    url: `${api_name}/saveCouponRule`,
//    method: 'post',
//    data: rule
    @ApiOperation("添加优惠券规则")
    @PostMapping("saveCouponRule")
    public Result saveCouponRule(@RequestBody CouponRuleVo couponRuleVo) {
        couponInfoService.saveCouponRule(couponRuleVo);
        return Result.ok();
    }
}























































