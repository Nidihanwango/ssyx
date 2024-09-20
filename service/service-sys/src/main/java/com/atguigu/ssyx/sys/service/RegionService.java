package com.atguigu.ssyx.sys.service;

import com.atguigu.ssyx.model.sys.Region;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 地区服务接口
 */
public interface RegionService extends IService<Region> {
    /**
     * 根据关键字获取地区列表
     */
    List<Region> findRegionByKeyword(String keyword);
}
