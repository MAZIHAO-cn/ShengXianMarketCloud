package com.mazihao.market.cart_order.service.impl;

import com.mazihao.market.cart_order.feign.ProductFeignClient;
import com.mazihao.market.cart_order.model.dao.CartMapper;
import com.mazihao.market.cart_order.model.pojo.Cart;
import com.mazihao.market.cart_order.model.vo.CartVO;
import com.mazihao.market.cart_order.service.CartService;
import com.mazihao.market.category_product.model.dao.ProductMapper;
import com.mazihao.market.category_product.model.pojo.Product;
import com.mazihao.market.common.common.Constant;
import com.mazihao.market.common.exception.shengxianmarketException;
import com.mazihao.market.common.exception.shengxianmarketExceptionEnum;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Resource
    public ProductFeignClient productFeignClient;

    @Resource
    public CartMapper cartMapper;

    @Override
    public List<CartVO> list(Integer userId) {
        List<CartVO> cartVOS = cartMapper.selectList(userId);
        for (int i = 0; i < cartVOS.size(); i++) {
            CartVO cartVO = cartVOS.get(i);
            int totalPrice = cartVO.getPrice() * cartVO.getQuantity();
            cartVO.setTotalPrice(totalPrice);
        }
        return cartVOS;
    }

    @Override
    public List<CartVO> add(Integer userId, Integer productId, Integer count) {
        // 检验商品是否有效
        validProduct(productId, count);

        Cart cart = cartMapper.selectCartByUserIdAndProductId(userId, productId);
        if (cart == null) {
            // 此商品不在购物车里，需要新增一个购物车记录
            cart = new Cart();
            cart.setProductId(productId);
            cart.setUserId(userId);
            cart.setQuantity(count);
            cart.setSelected(Constant.Cart.CHECKED);
            cartMapper.insertSelective(cart);
        } else {
            // 此商品在购物车里，则数量增加
            count = cart.getQuantity() + count;
            Cart cartNew = new Cart();
            cartNew.setQuantity(count);
            cartNew.setId(cart.getId());
            cartNew.setProductId(cart.getProductId());
            cartNew.setUserId(cart.getUserId());
            cartNew.setSelected(Constant.Cart.CHECKED);
            cartMapper.updateByPrimaryKeySelective(cartNew);
        }
        return this.list(userId);
    }

    // 检验商品是否有效的function
    private void validProduct(Integer productId, Integer count) {
        Product product = productFeignClient.detailForFeign(productId);
        // 如果商品为空或者商品的状态是未上架
        if (product == null || product.getStatus().equals(Constant.SaleStatus.NOT_SALE)) {
            throw new shengxianmarketException(shengxianmarketExceptionEnum.NOT_SALE);
        }
        // 判断商品库存
        if (count > product.getStock()) {
            throw new shengxianmarketException(shengxianmarketExceptionEnum.NOT_ENOUGH);
        }
    }

    @Override
    public List<CartVO> update(Integer userId, Integer productId, Integer count) {
        // 检验商品是否有效
        validProduct(productId, count);

        Cart cart = cartMapper.selectCartByUserIdAndProductId(userId, productId);
        if (cart == null) {
            // 此商品不在购物车里，无法更新
            throw new shengxianmarketException(shengxianmarketExceptionEnum.UPDATE_FAILED);
        } else {
            // 此商品在购物车里，则更新数量
            Cart cartNew = new Cart();
            cartNew.setQuantity(count);
            cartNew.setId(cart.getId());
            cartNew.setProductId(cart.getProductId());
            cartNew.setUserId(cart.getUserId());
            cartNew.setSelected(Constant.Cart.CHECKED);
            cartMapper.updateByPrimaryKeySelective(cartNew);
        }
        return this.list(userId);
    }

    @Override
    public List<CartVO> delete(Integer userId, Integer productId) {
        Cart cart = cartMapper.selectCartByUserIdAndProductId(userId, productId);
        if (cart == null) {
            // 此商品不在购物车里，无法删除
            throw new shengxianmarketException(shengxianmarketExceptionEnum.DELETE_FAILED);
        } else {
            // 此商品在购物车里，则可以删除
            cartMapper.deleteByPrimaryKey(cart.getId());
        }
        return this.list(userId);
    }

    @Override
    public List<CartVO> selectOrNot(Integer userId, Integer productId, Integer selected) {
        Cart cart = cartMapper.selectCartByUserIdAndProductId(userId, productId);
        if (cart == null) {
            // 此商品不在购物车里，无法选中
            throw new shengxianmarketException(shengxianmarketExceptionEnum.UPDATE_FAILED);
        } else {
            // 此商品在购物车里，则可以选中/不选中
            cartMapper.selectOrNot(userId, productId, selected);
        }
        return this.list(userId);
    }

    @Override
    public List<CartVO> selectOrNotAll(Integer userId, Integer selected) {
        // 改变选中状态
        cartMapper.selectOrNot(userId, null, selected);
        return this.list(userId);
    }

}
