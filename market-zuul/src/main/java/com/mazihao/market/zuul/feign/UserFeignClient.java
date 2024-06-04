package com.mazihao.market.zuul.feign;

import com.mazihao.market.user.model.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(value = "market-user")
public interface UserFeignClient {
    @PostMapping("/checkAdminRole")
    public Boolean checkAdminRole(@RequestBody User user);
}
