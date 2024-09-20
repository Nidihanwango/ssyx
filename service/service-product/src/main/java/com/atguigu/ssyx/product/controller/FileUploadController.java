package com.atguigu.ssyx.product.controller;

import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.product.service.FileUploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
//@CrossOrigin
@Api(tags = "文件上传")
@RequestMapping("/admin/product/fileUpload")
public class FileUploadController {

    @Resource
    private FileUploadService fileUploadService;

    @ApiOperation("文件上传")
    @PostMapping("")
    public Result fileUpload(MultipartFile file) {
        String fileUrl = fileUploadService.fileUpload(file);
        return Result.ok(fileUrl);
    }
}
