package com.atguigu.ssyx.product.service.impl;

import com.atguigu.ssyx.product.mapper.CommentMapper;
import com.atguigu.ssyx.model.product.Comment;
import com.atguigu.ssyx.product.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
}
