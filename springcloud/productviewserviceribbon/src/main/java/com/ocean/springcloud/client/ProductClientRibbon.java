package com.ocean.springcloud.client;

import com.ocean.springcloud.pojo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

//@Component
@FeignClient(value = "PRODUCT-DATA-SERVICE")
public interface ProductClientRibbon {
//    @Autowired
//    RestTemplate restTemplate;

//    ribbon
//    public List<Product> listProdcuts() {
//        restTemplate.getForObject("http://PRODUCT-DATA-SERVICE/products",List.class);
//        return new ArrayList<Product>();
//    }

//    feign
    @GetMapping("/products")
    public List<Product> listProdcuts();
}
