package com.atguigu.ssyx.activity.mapper;

import com.atguigu.ssyx.model.activity.ActivitySku;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ActivitySkuMapper extends BaseMapper<ActivitySku> {
    // 找出集合中正在参与活动的商品
    List<Long> findSkuInActivity(@Param("skuIdList") List<Long> skuIdList);
}
