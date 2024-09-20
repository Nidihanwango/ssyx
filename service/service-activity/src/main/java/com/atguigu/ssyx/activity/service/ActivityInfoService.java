package com.atguigu.ssyx.activity.service;

import com.atguigu.ssyx.model.activity.ActivityInfo;
import com.atguigu.ssyx.model.product.SkuInfo;
import com.atguigu.ssyx.vo.activity.ActivityRuleVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

public interface ActivityInfoService extends IService<ActivityInfo> {
    // 分页查询活动列表
    IPage<ActivityInfo> getActivityPage(IPage<ActivityInfo> pageParam);

    // 获取活动规则
    Map<String, Object> findActivityRuleList(Long id);

    // 保存活动规则
    void saveActivityRule(ActivityRuleVo activityRuleVo);

    // 根据关键字匹配sku信息
    List<SkuInfo> findSkuInfoByKeyword(String keyword);
}
