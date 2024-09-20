package com.atguigu.ssyx.activity.service.impl;

import com.atguigu.ssyx.activity.mapper.CouponInfoMapper;
import com.atguigu.ssyx.activity.mapper.CouponRangeMapper;
import com.atguigu.ssyx.activity.service.CouponInfoService;
import com.atguigu.ssyx.client.product.ProductFeignClient;
import com.atguigu.ssyx.enums.CouponRangeType;
import com.atguigu.ssyx.model.activity.CouponInfo;
import com.atguigu.ssyx.model.activity.CouponRange;
import com.atguigu.ssyx.model.product.Category;
import com.atguigu.ssyx.model.product.SkuInfo;
import com.atguigu.ssyx.vo.activity.CouponRuleVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CouponInfoServiceImpl extends ServiceImpl<CouponInfoMapper, CouponInfo> implements CouponInfoService {
    @Resource
    private CouponRangeMapper couponRangeMapper;

    @Resource
    private ProductFeignClient productFeignClient;

    // 分页查询
    @Override
    public IPage<CouponInfo> selectPage(Page<CouponInfo> pageParam) {
        IPage<CouponInfo> pageModel = this.page(pageParam);
        List<CouponInfo> couponInfoList = pageModel.getRecords();
        couponInfoList.forEach(item -> {
            item.setCouponTypeString(item.getCouponType().getComment());
            CouponRangeType rangeType = item.getRangeType();
            if (rangeType != null) {
                item.setRangeTypeString(rangeType.getComment());
            }
        });
        return pageModel;
    }

    @Override
    public CouponInfo getCouponInfoById(Long id) {
        CouponInfo couponInfo = this.getById(id);
        couponInfo.setCouponTypeString(couponInfo.getCouponType().getComment());
        if (couponInfo.getRangeType() != null) {
            couponInfo.setRangeTypeString(couponInfo.getRangeType().getComment());
        }
        return couponInfo;
    }

    // 根据id获取优惠券规则
    @Override
    public Map<String, Object> findCouponRuleList(Long id) {
        Map<String, Object> result = new HashMap<>();
        // 1.获取优惠券基本信息
        CouponInfo couponInfo = this.getById(id);
        // 2.获取range_id
        List<CouponRange> couponRangeList = couponRangeMapper.selectList(new LambdaQueryWrapper<CouponRange>().eq(CouponRange::getCouponId, id));
        if (CollectionUtils.isEmpty(couponRangeList)) {
            return result;
        }
//        result.put("couponRangeList", couponRangeList);
        List<Long> rangeIdList = couponRangeList.stream().map(CouponRange::getRangeId).collect(Collectors.toList());
        // 3.判断range_type,远程调用product模块,获取range_id对应的数据
        if (couponInfo.getRangeType().getCode() == 2) {
            List<SkuInfo> skuInfoList = productFeignClient.getSkuInfoList(rangeIdList);
            result.put("skuInfoList", skuInfoList);
        } else if (couponInfo.getRangeType().getCode() == 3) {
            List<Category> categoryList = productFeignClient.getCategoryList(rangeIdList);
            result.put("categoryList", categoryList);
        }
        // 4.封装返回
        return result;
    }

    // 添加优惠券规则
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveCouponRule(CouponRuleVo couponRuleVo) {

    }
}
