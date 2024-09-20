package com.atguigu.ssyx.sys.controller;

import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.sys.service.WareService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

//@CrossOrigin
@Api(tags = "仓库管理")
@RestController
@RequestMapping("/admin/sys/ware")
public class WareController {
    @Resource
    private WareService wareService;

    @ApiOperation("获取所有仓库")
    @GetMapping("findAllList")
    public Result findAllList(){
        return Result.ok(wareService.list());
    }
}
