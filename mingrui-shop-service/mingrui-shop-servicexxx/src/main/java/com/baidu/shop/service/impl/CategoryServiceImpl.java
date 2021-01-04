package com.baidu.shop.service.impl;

import com.baidu.shop.base.BaseApiService;
import com.baidu.shop.base.Result;
import com.baidu.shop.entity.CategoryBrandEntity;
import com.baidu.shop.entity.CategoryEntity;
import com.baidu.shop.mapper.CategoryBrandMapper;
import com.baidu.shop.mapper.CategoryMapper;
import com.baidu.shop.service.CategoryService;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName CategoryServiceImpl
 * @Description: TODO
 * @Author hexiangshen
 * @Date 2020/12/22
 * @Version V1.0
 **/
@RestController
public class CategoryServiceImpl extends BaseApiService implements CategoryService {
    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private CategoryBrandMapper categoryBrandMapper;

    @Override
    public Result<List<CategoryEntity>> getCategoryByBrandId(Integer brandId) {
        List<CategoryEntity>list = categoryMapper.getCategoryByBrandId(brandId);
        return this.setResultSuccess(list);
    }

    @Transactional
    @Override//新增
    public Result<Object> saveCategory(CategoryEntity categoryEntity) {
//        if (categoryEntity.getIsParent() != 1){
//            CategoryEntity cate = new CategoryEntity();
//            cate.setIsParent(1);
//            categoryMapper.updateByPrimaryKeySelective(cate);
//        }

        CategoryEntity cate = new CategoryEntity();
        cate.setId(categoryEntity.getParentId());
        cate.setIsParent(1);
        categoryMapper.updateByPrimaryKeySelective(cate);

        categoryMapper.insertSelective(categoryEntity);
        return this.setResultSuccess();
    }

    @Transactional
    @Override//修改
    public Result<Object> editCategory(CategoryEntity categoryEntity) {
        categoryMapper.updateByPrimaryKeySelective(categoryEntity);
        return this.setResultSuccess();
    }

    @Override//查询
    public Result<List<CategoryEntity>> getCategoryByPid(Integer pid) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setParentId(pid);
        List<CategoryEntity>list = categoryMapper.select(categoryEntity);
        return this.setResultSuccess(list);
    }

    @Transactional
    @Override//删除
    public Result<Object> deleteById(Integer id) {


        //判断前台传来的id是否合法
        if (null == id || id<= 0) return this.setResultError("id不合法");
        //根据id查询一条数据
        CategoryEntity categoryEntity = categoryMapper.selectByPrimaryKey(id);
        //判断查询一条数据是否存在
        if (null == categoryEntity) return  this.setResultError("数据不存在");
        //根据查询的数据的 判断isid是否为1 1为父节点不能删除
        if (categoryEntity.getIsParent() == 1) return this.setResultError("当前节点为父节点");

        CategoryBrandEntity categoryBrandEntity = new CategoryBrandEntity();
        categoryBrandEntity.setCategoryId(id);
        List<CategoryBrandEntity> cate =  categoryBrandMapper.select(categoryBrandEntity);
        if (cate.size() >= 1){
            return this.setResultError("存在中间表");
        }

        Example example = new Example(categoryEntity.getClass());
        example.createCriteria().andEqualTo("parentId",categoryEntity.getParentId());
        List<CategoryEntity>categoryEntityList = categoryMapper.selectByExample(example);

        if (categoryEntityList.size() <= 1){
            CategoryEntity UpdateCategory = new CategoryEntity();
            UpdateCategory.setIsParent(0);
            UpdateCategory.setId(categoryEntity.getParentId());
            categoryMapper.updateByPrimaryKeySelective(UpdateCategory);
        }

        categoryMapper.deleteByPrimaryKey(id);
        return this.setResultSuccess();
    }
}