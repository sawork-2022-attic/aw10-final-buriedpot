package com.micropos.api.rest;


import com.micropos.api.dto.CartDto;
import com.micropos.api.dto.ItemDto;
import com.micropos.api.dto.ProductDto;
import com.micropos.api.feign.CartFeignClient;
import com.micropos.api.feign.ProductFeignClient;
import com.micropos.api.model.Account;
import com.micropos.api.reactivefeign.CartReactiveFeignClient;
import com.micropos.api.repository.AccountRepository;
import com.micropos.api.service.ApiService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api")
public class ApiController {


    @Autowired
    private ApiService apiService;

    @Autowired
    private ProductFeignClient productFeignClient;

    @Autowired
    private CartFeignClient cartFeignClient;

    @Autowired
    private AccountRepository accountRepository;

 /*   @Autowired
    private CartReactiveFeignClient cartReactiveFeignClient;*/

    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> showProducts() {
        ResponseEntity<List<ProductDto>> productDtos = productFeignClient.listProducts();
        return productDtos;
    }

    @GetMapping("/carts")
    public Flux<CartDto> showCarts() {
        return cartFeignClient.listCarts();
    }

    @GetMapping("/carts/cart/{cartId}")
    public Mono<CartDto> showCartById(@PathVariable("cartId") String cartId) {
        return cartFeignClient.showCartById(cartId);
    }

    @GetMapping("/carts/newcart/{accountId}")
    public Mono<CartDto> newCart(@PathVariable("accountId") String accountId) {
        //Mono<CartDto> cartDto = cartFeignClient.newCart(accountId);
        //return cartDto;
        return accountRepository.findById(accountId).flatMap(account -> {
            return cartFeignClient.newCart(accountId);
        });
    }

    @PostMapping("/carts/cart/{cartId}")
    public Mono<CartDto> addItemToCart(@PathVariable("cartId") String cartId, @RequestBody ItemDto itemDto) {
        /*return cartService.add(cartId, itemMapper.toItem(itemDto)).map(cart -> new ResponseEntity<>(
                cartMapper.toCartDto(cart), HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));*/
        return cartFeignClient.addItemToCart(cartId, itemDto);
    }

    @GetMapping("/carts/checkout/{cartId}")
    public Mono<Double> checkout(@PathVariable("cartId") String cartId) {
        /*return cartService.checkout(cartId).map(ret -> new ResponseEntity<>(ret, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));*/
        return cartFeignClient.checkout(cartId);
    }

    @GetMapping("/register/{accountId}")
    public Mono<Account> register(@PathVariable("accountId") String accountId) {
        /*return cartService.checkout(cartId).map(ret -> new ResponseEntity<>(ret, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));*/
        return apiService.register(accountId);
    }


    @GetMapping("/delete/{accountId}")
    public Mono<Void> delete(@PathVariable("accountId") String accountId) {
        /*return cartService.checkout(cartId).map(ret -> new ResponseEntity<>(ret, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));*/
        return apiService.delete(accountId);
    }


/*    @GetMapping("/newcart")
    public ResponseEntity<CartDto> showCartById() {
        ResponseEntity<CartDto> cartDto = cartFeignClient.showCartById(cartId);
        return cartDto;
    }*/
}
