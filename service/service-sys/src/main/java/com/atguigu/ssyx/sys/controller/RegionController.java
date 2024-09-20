package com.atguigu.ssyx.sys.controller;

import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.sys.service.RegionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

//@CrossOrigin
@Api(tags = "区域管理")
@RestController
@RequestMapping("/admin/sys/region")
public class RegionController {
    @Resource
    private RegionService regionService;

    @ApiOperation("根据关键字获取地区列表")
    @GetMapping("/findRegionByKeyword/{keyword}")
    public Result findRegionByKeyword(@PathVariable String keyword){
        return Result.ok(regionService.findRegionByKeyword(keyword));
    }
}
