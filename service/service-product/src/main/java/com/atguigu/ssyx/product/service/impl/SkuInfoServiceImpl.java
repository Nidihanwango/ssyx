package com.atguigu.ssyx.product.service.impl;

import com.atguigu.ssyx.common.constant.MqConst;
import com.atguigu.ssyx.common.service.RabbitService;
import com.atguigu.ssyx.model.product.SkuAttrValue;
import com.atguigu.ssyx.model.product.SkuImage;
import com.atguigu.ssyx.model.product.SkuInfo;
import com.atguigu.ssyx.model.product.SkuPoster;
import com.atguigu.ssyx.product.mapper.SkuInfoMapper;
import com.atguigu.ssyx.product.service.SkuAttrValueService;
import com.atguigu.ssyx.product.service.SkuImageService;
import com.atguigu.ssyx.product.service.SkuInfoService;
import com.atguigu.ssyx.product.service.SkuPosterService;
import com.atguigu.ssyx.vo.product.SkuInfoQueryVo;
import com.atguigu.ssyx.vo.product.SkuInfoVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoMapper, SkuInfo> implements SkuInfoService {

    @Resource
    private SkuImageService skuImageService;

    @Resource
    private SkuPosterService skuPosterService;

    @Resource
    private SkuAttrValueService skuAttrValueService;

    @Resource
    private RabbitService rabbitService;

    // 分页查询
    @Override
    public IPage<SkuInfo> getPage(Page<SkuInfo> pageParam, SkuInfoQueryVo vo) {
        LambdaQueryWrapper<SkuInfo> wrapper = new LambdaQueryWrapper<>();
        String skuType = vo.getSkuType();
        Long categoryId = vo.getCategoryId();
        String keyword = vo.getKeyword();
        if (!StringUtils.isEmpty(skuType)) {
            wrapper.eq(SkuInfo::getSkuType, skuType);
        }
        if (categoryId != null) {
            wrapper.eq(SkuInfo::getCategoryId, categoryId);
        }
        if (!StringUtils.isEmpty(keyword)) {
            wrapper.like(SkuInfo::getSkuName, keyword);
        }
        return this.page(pageParam, wrapper);
    }

    // 添加商品
    @Override
    @Transactional
    public void saveSkuInfoVo(SkuInfoVo skuInfoVo) {
        // 1.保存skuInfo基础属性
        SkuInfo skuInfo = new SkuInfo();
        BeanUtils.copyProperties(skuInfoVo, skuInfo);
        this.save(skuInfo);
        // 2.保存商品图片
        List<SkuImage> skuImagesList = skuInfoVo.getSkuImagesList();
        if (!CollectionUtils.isEmpty(skuImagesList)) {
            int sort = 1;
            for (SkuImage skuImage : skuImagesList) {
                skuImage.setSkuId(skuInfo.getId());
                skuImage.setSort(sort++);
            }
            skuImageService.saveBatch(skuImagesList);
        }
        // 3.保存商品海报
        List<SkuPoster> skuPosterList = skuInfoVo.getSkuPosterList();
        if (!CollectionUtils.isEmpty(skuPosterList)) {
            for (SkuPoster skuPoster : skuPosterList) {
                skuPoster.setSkuId(skuInfo.getId());
            }
            skuPosterService.saveBatch(skuPosterList);
        }
        // 4.保存商品属性
        List<SkuAttrValue> skuAttrValueList = skuInfoVo.getSkuAttrValueList();
        if (!CollectionUtils.isEmpty(skuAttrValueList)) {
            int sort = 1;
            for (SkuAttrValue skuAttrValue : skuAttrValueList) {
                skuAttrValue.setSkuId(skuInfo.getId());
                skuAttrValue.setSort(sort++);
            }
            skuAttrValueService.saveBatch(skuAttrValueList);
        }
    }
    // 获取商品信息
    @Override
    public SkuInfoVo getSkuInfoVoById(Long id) {
        // 1.获取sku基本信息
        SkuInfo skuInfo = this.getById(id);
        // 2.获取sku图片
        List<SkuImage> skuImages = skuImageService.list(new LambdaQueryWrapper<SkuImage>().eq(SkuImage::getSkuId, id));
        // 3.获取sku海报
        List<SkuPoster> skuPosters = skuPosterService.list(new LambdaQueryWrapper<SkuPoster>().eq(SkuPoster::getSkuId, id));
        // 4.获取sku基本属性
        List<SkuAttrValue> skuAttrValues = skuAttrValueService.list(new LambdaQueryWrapper<SkuAttrValue>().eq(SkuAttrValue::getSkuId, id));
        // 5.构造SkuInfoVo
        SkuInfoVo skuInfoVo = new SkuInfoVo();
        BeanUtils.copyProperties(skuInfo, skuInfoVo);
        skuInfoVo.setSkuImagesList(skuImages);
        skuInfoVo.setSkuPosterList(skuPosters);
        skuInfoVo.setSkuAttrValueList(skuAttrValues);
        return skuInfoVo;
    }
    // 修改商品
    @Override
    @Transactional
    public void updateSkuInfoVo(SkuInfoVo skuInfoVo) {
        Long id = skuInfoVo.getId();
        // 1.更新skuInfo基础信息
        this.removeById(id);
        SkuInfo skuInfo = new SkuInfo();
        BeanUtils.copyProperties(skuInfoVo, skuInfo);
        this.save(skuInfo);
        // 2.更新sku图片
        skuImageService.remove(new LambdaQueryWrapper<SkuImage>().eq(SkuImage::getSkuId, id));
        List<SkuImage> skuImagesList = skuInfoVo.getSkuImagesList();
        if (!CollectionUtils.isEmpty(skuImagesList)) {
            int sort = 1;
            for (SkuImage skuImage : skuImagesList) {
                skuImage.setSkuId(skuInfo.getId());
                skuImage.setSort(sort++);
            }
            skuImageService.saveBatch(skuImagesList);
        }
        // 3.更新sku海报
        skuPosterService.remove(new LambdaQueryWrapper<SkuPoster>().eq(SkuPoster::getSkuId, id));
        List<SkuPoster> skuPosterList = skuInfoVo.getSkuPosterList();
        if (!CollectionUtils.isEmpty(skuPosterList)) {
            for (SkuPoster skuPoster : skuPosterList) {
                skuPoster.setSkuId(skuInfo.getId());
            }
            skuPosterService.saveBatch(skuPosterList);
        }
        // 4.更新sku属性
        skuAttrValueService.remove(new LambdaQueryWrapper<SkuAttrValue>().eq(SkuAttrValue::getSkuId, id));
        List<SkuAttrValue> skuAttrValueList = skuInfoVo.getSkuAttrValueList();
        if (!CollectionUtils.isEmpty(skuAttrValueList)) {
            int sort = 1;
            for (SkuAttrValue skuAttrValue : skuAttrValueList) {
                skuAttrValue.setSkuId(skuInfo.getId());
                skuAttrValue.setSort(sort++);
            }
            skuAttrValueService.saveBatch(skuAttrValueList);
        }
    }
    // 审核商品
    @Override
    public void check(Long skuId, Integer status) {
        SkuInfo skuInfo = new SkuInfo();
        skuInfo.setId(skuId);
        skuInfo.setCheckStatus(status);
        this.updateById(skuInfo);
    }
    // 商品上下架
    @Override
    public void publish(Long skuId, Integer status) {
        SkuInfo skuInfo = new SkuInfo();
        skuInfo.setId(skuId);
        skuInfo.setPublishStatus(status);
        this.updateById(skuInfo);
        if (status == 1) {
            //商品上架：发送mq消息同步es
            rabbitService.sendMessage(MqConst.EXCHANGE_GOODS_DIRECT, MqConst.ROUTING_GOODS_UPPER, skuId);
        } else {
            //商品下架：发送mq消息同步es
            rabbitService.sendMessage(MqConst.EXCHANGE_GOODS_DIRECT, MqConst.ROUTING_GOODS_LOWER, skuId);
        }
    }
    // 新人专享
    @Override
    public void updateIsNewPersonStatus(Long skuId, Integer status) {
        SkuInfo skuInfo = new SkuInfo();
        skuInfo.setId(skuId);
        skuInfo.setIsNewPerson(status);
        this.updateById(skuInfo);
    }
    // 根据关键字获取sku
    @Override
    public List<SkuInfo> getSkuByKeyword(String keyword) {
        return this.list(new LambdaQueryWrapper<SkuInfo>().like(SkuInfo::getSkuName, keyword));
    }
}
