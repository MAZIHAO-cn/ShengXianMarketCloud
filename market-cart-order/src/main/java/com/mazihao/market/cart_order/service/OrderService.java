package com.mazihao.market.cart_order.service;

import com.github.pagehelper.PageInfo;
import com.google.zxing.WriterException;
import com.mazihao.market.cart_order.model.vo.OrderVO;
import com.mazihao.market.cart_order.request.CreateOrderReq;

import java.io.IOException;

public interface OrderService {

    String create(CreateOrderReq createOrderReq);

    OrderVO detail(String orderNo);

    PageInfo listForCustomer(Integer pageNum, Integer pageSize);

    void cancel(String orderNo);

    String qrcode(String orderNo) throws IOException, WriterException;

    PageInfo listForAdmin(Integer pageNum, Integer pageSize);

    void pay(String orderNo);

    void delivered(String orderNo);

    void finished(String orderNo);
}
