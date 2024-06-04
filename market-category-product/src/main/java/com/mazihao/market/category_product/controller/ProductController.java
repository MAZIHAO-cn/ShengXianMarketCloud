package com.mazihao.market.category_product.controller;

import com.github.pagehelper.PageInfo;
import com.mazihao.market.category_product.model.pojo.Product;
import com.mazihao.market.category_product.model.request.ProductListReq;
import com.mazihao.market.category_product.service.ProductService;
import com.mazihao.market.common.common.ApiRestResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 前台商品Controller
 */
@RestController
public class ProductController {
    @Resource
    private ProductService productService;

    @ApiOperation("商品详情")
    @GetMapping("product/detail")
    public ApiRestResponse detail(@RequestParam Integer id) {
        Product product = productService.detail(id);
        return ApiRestResponse.success(product);
    }

    @ApiOperation("商品列表")
    @GetMapping("product/list")
    public ApiRestResponse list(ProductListReq productListReq) {
        PageInfo list = productService.list(productListReq);
        return ApiRestResponse.success(list);
    }

    @ApiOperation("商品详情")
    @GetMapping("product/detailForFeign")
    public Product detailForFeign(@RequestParam Integer id) {
        Product product = productService.detail(id);
        return product;
    }

    @PostMapping("product/updateStock")
    public void updateStock(@RequestParam Integer productId, @RequestParam Integer stock) {
        productService.updateStock(productId, stock);
    }
}
