package com.atguigu.ssyx.vo.product;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CategoryQueryVo {
	
	@ApiModelProperty(value = "分类名称")
	private java.lang.String name;

}

