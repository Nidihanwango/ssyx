package com.atguigu.ssyx.product.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.atguigu.ssyx.product.service.FileUploadService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * 文件上传服务
 */
@Service
public class FileUploadServiceImpl implements FileUploadService {
    @Value("${aliyun.endpoint}")
    private String endpoint;
    @Value("${aliyun.keyId}")
    private String keyId;
    @Value("${aliyun.keySecret}")
    private String keySecret;
    @Value("${aliyun.bucketName}")
    private String bucketName;

    // 文件上传
    @Override
    public String fileUpload(MultipartFile file) {
        // 1.创建OSSClient实例
        OSS ossClient = new OSSClientBuilder().build(endpoint, keyId, keySecret);
        try {
            // 2.获取文件流
            InputStream inputStream = file.getInputStream();
            // 3.生成文件路径
            String filename = file.getOriginalFilename();
            String uuid = UUID.randomUUID().toString().replace("-", "");
            filename = uuid + filename;
            String timeUrl = new DateTime().toString("yyyy/MM/dd");
            filename = timeUrl + "/" + filename;
            // 4.调用方法上传
            ossClient.putObject(bucketName, filename, inputStream);
            // 5.返回文件路径
            return "https://" + bucketName + "." + endpoint + "/" + filename;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }
}
