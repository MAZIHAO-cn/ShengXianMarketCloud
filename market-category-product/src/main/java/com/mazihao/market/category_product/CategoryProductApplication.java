package com.mazihao.market.category_product;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication
@EnableRedisHttpSession
@EnableFeignClients
@MapperScan(basePackages = "com.mazihao.market.category_product.model.dao")
public class CategoryProductApplication {
    public static void main(String[] args) {
        SpringApplication.run(CategoryProductApplication.class, args);
    }
}
