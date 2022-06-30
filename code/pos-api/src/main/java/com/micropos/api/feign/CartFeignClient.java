package com.micropos.api.feign;


import com.micropos.api.dto.CartDto;
import com.micropos.api.dto.ItemDto;
import com.micropos.api.dto.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/*@FeignClient(name = "poscarts")*/
/*public interface CartFeignClient {
    @GetMapping("/carts")
    public ResponseEntity<List<CartDto>> listCarts();
    @GetMapping("/carts/cart/{cartId}")
    public ResponseEntity<CartDto> showCartById(@PathVariable(value = "cartId") Integer cartId);
}*/

@ReactiveFeignClient(name = "poscarts")
public interface CartFeignClient {
    @GetMapping("/carts")
    public Flux<CartDto> listCarts();

    @GetMapping("/carts/cart/{cartId}")
    public Mono<CartDto> showCartById(@PathVariable(value = "cartId") String cartId);

    @GetMapping("/carts/newcart/{accountId}")
    public Mono<CartDto> newCart(@PathVariable(value = "accountId") String accountId);

    @PostMapping("/carts/cart/{cartId}")
    public Mono<CartDto> addItemToCart(@PathVariable("cartId") String cartId, @RequestBody ItemDto itemDto);

    @GetMapping("/carts/checkout/{cartId}")
    public Mono<Double> checkout(@PathVariable("cartId") String cartId);
}


