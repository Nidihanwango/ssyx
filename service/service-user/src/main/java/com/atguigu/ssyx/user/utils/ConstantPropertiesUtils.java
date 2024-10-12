package com.atguigu.ssyx.user.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("wx.open")

public class ConstantPropertiesUtils {
    private String appId;
    private String appSecret;
}
