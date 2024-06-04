package com.mazihao.market.category_product.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mazihao.market.category_product.model.dao.CategoryMapper;
import com.mazihao.market.category_product.model.pojo.Category;
import com.mazihao.market.category_product.model.request.AddCategoryReq;
import com.mazihao.market.category_product.model.vo.CategoryVO;
import com.mazihao.market.category_product.service.CategoryService;
import com.mazihao.market.common.exception.shengxianmarketException;
import com.mazihao.market.common.exception.shengxianmarketExceptionEnum;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 目录分类Service实现类
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Resource
    CategoryMapper categoryMapper;

    @Override
    public void add(AddCategoryReq addCategoryReq) {
        Category category = new Category();
        BeanUtils.copyProperties(addCategoryReq, category);
        Category categoryOld = categoryMapper.selectByName(addCategoryReq.getName());
        if (categoryOld != null) {
            throw new shengxianmarketException(shengxianmarketExceptionEnum.NAME_NOT_PERMIT_SAME);
        }
        int count = categoryMapper.insertSelective(category);
        if (count == 0) {
            throw new shengxianmarketException(shengxianmarketExceptionEnum.CREATE_FAILED);
        }
    }

    @Override
    public void update(Category updateCategory) {
        if (updateCategory.getName() != null) {
            Category categoryOld = categoryMapper.selectByName(updateCategory.getName());
            if (categoryOld != null && !categoryOld.getId().equals(updateCategory.getId())) {
                throw new shengxianmarketException(shengxianmarketExceptionEnum.NAME_NOT_PERMIT_SAME);
            }
            int count = categoryMapper.updateByPrimaryKeySelective(updateCategory);
            if (count == 0) {
                throw new shengxianmarketException(shengxianmarketExceptionEnum.UPDATE_FAILED);
            }
        }
    }

    @Override
    public void delete(Integer id) {
        Category categoryOld = categoryMapper.selectByPrimaryKey(id);
        if (categoryOld == null) {
            throw new shengxianmarketException(shengxianmarketExceptionEnum.DELETE_FAILED);
        }
        int count = categoryMapper.deleteByPrimaryKey(id);
        if (count == 0) {
            throw new shengxianmarketException(shengxianmarketExceptionEnum.DELETE_FAILED);
        }
    }

    @Override
    public PageInfo listForAdmin(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize, "type, order_num");
        List<Category> categoryList = categoryMapper.selectList();
        PageInfo pageInfo = new PageInfo(categoryList);
        return pageInfo;
    }

    @Override
    @Cacheable(value = "listCategoryForCustomer")
    public List<CategoryVO> listCategoryForCustomer(Integer parentId) {
        ArrayList<CategoryVO> categoryVOList = new ArrayList<>();
        recursivelyFindCategories(categoryVOList, parentId);
        return categoryVOList;
    }

    // 递归获取所有子类别，并组合成为一个"目录树"
    private void recursivelyFindCategories(List<CategoryVO> categoryVOList, Integer parentId) {
        List<Category> categoryList = categoryMapper.selectCategoriesByParentId(parentId);
        if (!CollectionUtils.isEmpty(categoryList)) {
            for (int i = 0; i < categoryList.size(); i++) {
                Category category = categoryList.get(i);
                CategoryVO categoryVO = new CategoryVO();
                BeanUtils.copyProperties(category, categoryVO);
                categoryVOList.add(categoryVO);
                recursivelyFindCategories(categoryVO.getChildCategory(), categoryVO.getId());
            }
        }
    }
}
