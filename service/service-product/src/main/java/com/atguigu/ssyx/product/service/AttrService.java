package com.atguigu.ssyx.product.service;

import com.atguigu.ssyx.model.product.Attr;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 平台属性服务接口
 */
public interface AttrService extends IService<Attr> {
    // 根据分组id获取
    List<Attr> getByGroupId(Long groupId);
}
