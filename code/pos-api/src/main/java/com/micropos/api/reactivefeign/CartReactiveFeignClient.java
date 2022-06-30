package com.micropos.api.reactivefeign;


import com.micropos.api.dto.CartDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Flux;


public interface CartReactiveFeignClient {
    @GetMapping("/carts")
    public Flux<CartDto> listCarts();
}
