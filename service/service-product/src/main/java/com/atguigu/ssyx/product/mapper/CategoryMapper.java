package com.atguigu.ssyx.product.mapper;

import com.atguigu.ssyx.model.product.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品分类mapper
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}
