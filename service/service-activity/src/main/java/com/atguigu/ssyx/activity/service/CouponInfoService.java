package com.atguigu.ssyx.activity.service;

import com.atguigu.ssyx.model.activity.CouponInfo;
import com.atguigu.ssyx.vo.activity.CouponRuleVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

public interface CouponInfoService extends IService<CouponInfo> {
    // 分页查询
    IPage<CouponInfo> selectPage(Page<CouponInfo> pageParam);
    // 根据id查询
    CouponInfo getCouponInfoById(Long id);
    // 根据id获取优惠券规则
    Map<String, Object> findCouponRuleList(Long id);
    // 添加优惠券规则
    void saveCouponRule(CouponRuleVo couponRuleVo);
}
