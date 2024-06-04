package com.mazihao.market.category_product.service;

import com.github.pagehelper.PageInfo;
import com.mazihao.market.category_product.model.pojo.Product;
import com.mazihao.market.category_product.model.request.AddProductReq;
import com.mazihao.market.category_product.model.request.ProductListReq;

/**
 * 商品Service
 */
public interface ProductService {

    void add(AddProductReq addProductReq);

    void update(Product updateProduct);

    void delete(Integer id);

    void batchUpdateSellStatus(Integer[] ids, Integer sellStatus);

    PageInfo listForAdmin(Integer pageNum, Integer pageSize);

    Product detail(Integer id);

    PageInfo list(ProductListReq productListReq);

    void updateStock(Integer productId, Integer stock);
}
