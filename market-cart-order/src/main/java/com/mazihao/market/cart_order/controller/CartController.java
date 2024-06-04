package com.mazihao.market.cart_order.controller;
import com.mazihao.market.cart_order.feign.UserFeignClient;
import com.mazihao.market.cart_order.model.vo.CartVO;
import com.mazihao.market.cart_order.service.CartService;
import com.mazihao.market.common.common.ApiRestResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Resource
    public CartService cartService;

    @Resource
    public UserFeignClient userFeignClient;

    @GetMapping("/list")
    @ApiOperation("购物车列表")
    public ApiRestResponse list() {
        // 内部获取用户ID，防止横向越权
        List<CartVO> cartList = cartService.list(userFeignClient.getuser().getId());
        return ApiRestResponse.success(cartList);
    }

    @PostMapping("/add")
    @ApiOperation("添加商品到购物车")
    public ApiRestResponse add(@RequestParam Integer productId, @RequestParam Integer count) {
        int userId = userFeignClient.getuser().getId();
        List<CartVO> cartVOList = cartService.add(userId, productId, count);
        return ApiRestResponse.success(cartVOList);
    }

    @PostMapping("/update")
    @ApiOperation("更新购物车")
    public ApiRestResponse update(@RequestParam Integer productId, @RequestParam Integer count) {
        int userId = userFeignClient.getuser().getId();
        List<CartVO> cartVOList = cartService.update(userId, productId, count);
        return ApiRestResponse.success(cartVOList);
    }

    @PostMapping("/delete")
    @ApiOperation("删除购物车")
    public ApiRestResponse delete(@RequestParam Integer productId) {
        // 不能传入userId， cartId也不能，否则可以删除别人的购物车
        int userId = userFeignClient.getuser().getId();
        List<CartVO> cartVOList = cartService.delete(userId, productId);
        return ApiRestResponse.success(cartVOList);
    }

    @PostMapping("/select")
    @ApiOperation("选择/不选择购物车的某商品")
    public ApiRestResponse select(@RequestParam Integer productId, @RequestParam Integer selected) {
        // 不能传入userId， cartId也不能，否则可以删除别人的购物车
        int userId = userFeignClient.getuser().getId();
        List<CartVO> cartVOList = cartService.selectOrNot(userId, productId, selected);
        return ApiRestResponse.success(cartVOList);
    }

    @PostMapping("/selectAll")
    @ApiOperation("全选/全不选购物车")
    public ApiRestResponse selectAll(@RequestParam Integer selected) {
        // 不能传入userId， cartId也不能，否则可以删除别人的购物车
        int userId = userFeignClient.getuser().getId();
        List<CartVO> cartVOList = cartService.selectOrNotAll(userId, selected);
        return ApiRestResponse.success(cartVOList);
    }

}
