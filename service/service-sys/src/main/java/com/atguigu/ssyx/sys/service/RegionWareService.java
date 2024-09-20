package com.atguigu.ssyx.sys.service;

import com.atguigu.ssyx.model.sys.RegionWare;
import com.atguigu.ssyx.vo.sys.RegionWareQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 开通区域服务接口
 */
public interface RegionWareService extends IService<RegionWare> {
    //开通区域列表
    IPage<RegionWare> selectPage(Page<RegionWare> pageParam,
                                 RegionWareQueryVo regionWareQueryVo);

    // 添加开通区域
    void saveRegionWare(RegionWare regionWare);
    // 取消开通区域
    void updateStatus(Long id, Integer status);
}
