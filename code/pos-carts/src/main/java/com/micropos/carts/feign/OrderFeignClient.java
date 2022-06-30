package com.micropos.carts.feign;

import com.micropos.api.dto.CartDto;
import com.micropos.api.dto.OrderDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

import java.util.List;

@ReactiveFeignClient(name = "posorders")
public interface OrderFeignClient {
    @PostMapping("/orders/neworder")
    public @ResponseBody Mono<OrderDto> newOrder(@RequestBody CartDto cartDto);
}
