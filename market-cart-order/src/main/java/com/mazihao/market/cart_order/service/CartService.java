package com.mazihao.market.cart_order.service;

import com.mazihao.market.cart_order.model.vo.CartVO;

import java.util.List;

public interface CartService {

    List<CartVO> list(Integer userId);

    List<CartVO> add(Integer userId, Integer productId, Integer count);

    List<CartVO> update(Integer userId, Integer productId, Integer count);

    List<CartVO> delete(Integer userId, Integer productId);

    List<CartVO> selectOrNot(Integer userId, Integer productId, Integer selected);

    List<CartVO> selectOrNotAll(Integer userId, Integer selected);
}
