package com.mazihao.market.cart_order.feign;

import com.mazihao.market.user.model.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;


@FeignClient(name = "market-user")
public interface UserFeignClient {
    @GetMapping("/getuser")
    public User getuser();
}
