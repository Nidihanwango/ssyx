package com.atguigu.ssyx.activity.service.impl;

import com.atguigu.ssyx.activity.mapper.ActivityInfoMapper;
import com.atguigu.ssyx.activity.mapper.ActivityRuleMapper;
import com.atguigu.ssyx.activity.mapper.ActivitySkuMapper;
import com.atguigu.ssyx.activity.service.ActivityInfoService;
import com.atguigu.ssyx.client.product.ProductFeignClient;
import com.atguigu.ssyx.enums.ActivityType;
import com.atguigu.ssyx.model.activity.ActivityInfo;
import com.atguigu.ssyx.model.activity.ActivityRule;
import com.atguigu.ssyx.model.activity.ActivitySku;
import com.atguigu.ssyx.model.product.SkuInfo;
import com.atguigu.ssyx.vo.activity.ActivityRuleVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ActivityInfoServiceImpl extends ServiceImpl<ActivityInfoMapper, ActivityInfo> implements ActivityInfoService {
    @Resource
    private ActivityRuleMapper activityRuleMapper;
    @Resource
    private ActivitySkuMapper activitySkuMapper;
    @Resource
    private ProductFeignClient productFeignClient;

    // 分页查询活动列表
    @Override
    public IPage<ActivityInfo> getActivityPage(IPage<ActivityInfo> pageParam) {
        IPage<ActivityInfo> pageModel = this.page(pageParam);
        List<ActivityInfo> activityInfoList = pageModel.getRecords();
        activityInfoList.forEach(item -> {
            item.setActivityTypeString(item.getActivityType().getComment());
        });
        return pageModel;
    }

    // 获取活动规则
    @Override
    public Map<String, Object> findActivityRuleList(Long id) {
        List<ActivityRule> activityRuleList = activityRuleMapper.selectList(new LambdaQueryWrapper<ActivityRule>().eq(ActivityRule::getActivityId, id));
        List<ActivitySku> activitySkuList = activitySkuMapper.selectList(new LambdaQueryWrapper<ActivitySku>().eq(ActivitySku::getActivityId, id));
        List<Long> skuIds = activitySkuList.stream().map(ActivitySku::getSkuId).collect(Collectors.toList());
        // 远程调用product模块获取sku信息
        List<SkuInfo> skuInfoList = productFeignClient.getSkuInfoList(skuIds);
        Map<String, Object> map = new HashMap<>();
        map.put("activityRuleList", activityRuleList);
        map.put("skuInfoList", skuInfoList);
        return map;
    }

    // 保存活动规则
    @Override
    public void saveActivityRule(ActivityRuleVo activityRuleVo) {
        // 删除旧数据
        Long activityId = activityRuleVo.getActivityId();
        activityRuleMapper.delete(new LambdaQueryWrapper<ActivityRule>().eq(ActivityRule::getActivityId, activityId));
        activitySkuMapper.delete(new LambdaQueryWrapper<ActivitySku>().eq(ActivitySku::getActivityId, activityId));
        // 保存新数据
        ActivityInfo activityInfo = this.getById(activityId);
        ActivityType activityType = activityInfo.getActivityType();
        activityRuleVo.getActivityRuleList().forEach(item -> {
            item.setActivityId(activityId);
            item.setActivityType(activityType);
            activityRuleMapper.insert(item);
        });
        activityRuleVo.getActivitySkuList().forEach(item -> {
            item.setActivityId(activityId);
            activitySkuMapper.insert(item);
        });

    }

    // 根据关键字匹配sku信息
    @Override
    public List<SkuInfo> findSkuInfoByKeyword(String keyword) {
        // 1.product模块编写接口,根据关键词匹配sku信息
        // 2.远程调用product模块,获取sku信息
        List<SkuInfo> skuInfoList = productFeignClient.getSkuByKeyword(keyword);
        // 3.判断是否有商品已经参加过优惠活动,把正在参加活动的商品排除
        if (CollectionUtils.isEmpty(skuInfoList)) {
            return null;
        }
        List<Long> skuIdList = skuInfoList.stream().map(SkuInfo::getId).collect(Collectors.toList());
        List<Long> inActivitySkuList = activitySkuMapper.findSkuInActivity(skuIdList);
        List<SkuInfo> result = skuInfoList.stream().filter(item -> !inActivitySkuList.contains(item.getId())).collect(Collectors.toList());
        return result;
    }

}























