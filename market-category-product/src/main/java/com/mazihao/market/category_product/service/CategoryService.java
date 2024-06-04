package com.mazihao.market.category_product.service;

import com.github.pagehelper.PageInfo;
import com.mazihao.market.category_product.model.pojo.Category;
import com.mazihao.market.category_product.model.request.AddCategoryReq;
import com.mazihao.market.category_product.model.vo.CategoryVO;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface CategoryService {

    void add(AddCategoryReq addCategoryReq);

    void update(Category updateCategory);

    void delete(Integer id);

    PageInfo listForAdmin(Integer pageNum, Integer pageSize);

    @Cacheable(value = "listCategoryForCustomer")
    List<CategoryVO> listCategoryForCustomer(Integer parentId);
}
