package com.atguigu.ssyx.product.mapper;

import com.atguigu.ssyx.model.product.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品评论mapper
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
}
