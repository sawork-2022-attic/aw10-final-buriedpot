package com.micropos.api.feign;


import com.micropos.api.dto.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactivefeign.spring.config.ReactiveFeignClient;

import java.util.List;

@FeignClient(name = "posproducts")
public interface ProductFeignClient {
    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> listProducts();
    @GetMapping("/products/{productId}")
    public ResponseEntity<ProductDto> showProductById(@PathVariable(value = "productId") String productId);




}
