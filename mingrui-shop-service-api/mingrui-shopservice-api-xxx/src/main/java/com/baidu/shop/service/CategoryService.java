package com.baidu.shop.service;

import com.baidu.shop.base.Result;
import com.baidu.shop.entity.CategoryEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javafx.scene.chart.ValueAxis;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName CategoryService
 * @Description: TODO
 * @Author hexiangshen
 * @Date 2020/12/22
 * @Version V1.0
 **/
@Api(tags = "商品分类接口")
public interface CategoryService {

    @ApiOperation(value = "通过查询商品分类")
    @GetMapping(value = "category/list")
    Result<List<CategoryEntity>> getCategoryByPid(Integer pid);

    @ApiOperation(value = "删除")
    @DeleteMapping(value = "category/del")
    Result<Object>deleteById(Integer id);

    @ApiOperation(value = "修改")
    @PutMapping(value = "category/edit")
    Result<Object>editCategory(@RequestBody CategoryEntity categoryEntity);

    @ApiOperation(value = "新增")
    @PostMapping(value = "category/save")
    Result<Object>saveCategory(@RequestBody CategoryEntity categoryEntity);

    @ApiOperation(value = "根据id查询")
    @GetMapping(value = "category/brand")
    Result<List<CategoryEntity>> getCategoryByBrandId(Integer brandId);
}
