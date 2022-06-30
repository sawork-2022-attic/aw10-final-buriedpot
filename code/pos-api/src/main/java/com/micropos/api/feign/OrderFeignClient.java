package com.micropos.api.feign;

import com.micropos.api.dto.CartDto;
import com.micropos.api.dto.OrderDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactivefeign.spring.config.ReactiveFeignClient;

import java.util.List;

@FeignClient(name = "posorders")
public interface OrderFeignClient {
    @GetMapping("/orders")
    public ResponseEntity<List<OrderDto>> listCarts();
    @GetMapping("/carts/order/{orderId}")
    public ResponseEntity<CartDto> showCartById(@PathVariable(value = "cartId") Integer cartId);
}
