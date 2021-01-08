package com.baidu.shop.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName SpuDetailDTO
 * @Description: TODO
 * @Author hexiangshen
 * @Date 2021/1/7
 * @Version V1.0
 **/
@ApiModel(value = "spu大字段数据层传输DTO")
@Data
public class SpuDetailDTO {

    @ApiModelProperty(value = "spu主键",example = "1")
    private Integer spuId;

    @ApiModelProperty(value = "商品描述信息")
    private String description;

    @ApiModelProperty(value = "通过规格参数数据")
    private String genericSpec;

    @ApiModelProperty(value = "特有规格参数及可选值信息,json格式")
    private String specialSpec;

    @ApiModelProperty(value = "包装清单")
    private String packingList;

    @ApiModelProperty(value = "售后服务")
    private String afterService;
}
