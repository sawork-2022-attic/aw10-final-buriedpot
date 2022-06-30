package com.micropos.carts.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.micropos.api.dto.CartDto;
import com.micropos.carts.feign.OrderFeignClient;
import com.micropos.carts.mapper.CartMapper;
import com.micropos.carts.mapper.ItemMapper;
import com.micropos.carts.model.Item;
import com.micropos.carts.repository.CartRepository;
import com.micropos.carts.model.Cart;
import com.micropos.carts.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.data.util.Streamable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    private CartRepository cartRepository;
    private ItemRepository itemRepository;
    private CartMapper cartMapper;
    private ItemMapper itemMapper;
    private OrderFeignClient orderFeignClient;


    private final String COUNTER_URL = "http://POS-COUNTER/counter/";

    @LoadBalanced
    private RestTemplate restTemplate;

    @Autowired
    public CartServiceImpl(OrderFeignClient orderFeignClient) {
        this.orderFeignClient = orderFeignClient;
    }

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Autowired
    public void setCartRepository(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }


    @Autowired
    public void setItemRepository(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Autowired
    public void setCartMapper(CartMapper cartMapper) {
        this.cartMapper = cartMapper;
    }

    @Autowired
    public void setItemMapper(ItemMapper itemMapper) {
        this.itemMapper = itemMapper;
    }

    /*
        public Integer test() {

            Integer test = restTemplate.getForObject(COUNTER_URL + "/test", Integer.class);
            return test;
        }

        @Override
        public Double checkout(Integer cartId) {
            Optional<Cart> cart = this.cartRepository.findById(cartId);

            if (cart.isEmpty()) return Double.valueOf(-1);

            return this.checkout(cart.get());
        }

        @Override
        public Double checkout(Cart cart) {
            return null;
        }
    */
    @Override
    public Mono<Cart> add(String cartId, Item item) {
        //itemRepository.save(item);
        return cartRepository.findById(cartId).flatMap(x -> {
            x.addItem(item);
            return cartRepository.save(x);
        });
    }
/*
    @Override
    public Cart modify(Cart cart, Item item) {
        return null;
    }

    @Override
    public Cart delete(Cart cart, String productId) {
        cart.deleteItem(productId);
        return cart;
    }

    @Override
    public Cart empty(Cart cart) {
        cart.empty();
        return cart;
    }*/


    @Override
    public Flux<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    @Override
    public Mono<Cart> getCart(String cartId) {
        return cartRepository.findById(cartId);
    }
/*
    @Override
    public Cart addCart(Cart cart) {
        return cartRepository.save(cart);
    }*/

    @Override
    @Deprecated
    public Mono<Cart> newCart() {
        Cart cart = new Cart();
        cart.cartId(null);
        return cartRepository.save(cart);
    }

    @Override
    public Mono<Cart> newCart(String accountId) {
        Cart cart = new Cart();
        cart.cartId(accountId);
        return cartRepository.insert(cart);
    }

    /**
     * one user can only have one cart
     * @param cartId
     * @return
     */
    @Override
    public Mono<Double> checkout(String cartId) {
        return cartRepository.findById(cartId).flatMap(cart -> {
            double total = 0;
            for (int i = 0; i < cart.items().size(); i++) {
                total += cart.items().get(i).quantity() * cart.items().get(i).productPrice();
            }
            orderFeignClient.newOrder(cartMapper.toCartDto(cart)).subscribe(System.out::println);
            return this.cartRepository.delete(cart)
                    .then(Mono.just(total));
        }).map(d -> d);
    }

    /**
     * one user can only have one cart. Checkout by accountId
     * @param accountId
     * @return
     */
    /*@Override
    public Mono<Double> checkoutByAccoundId(String accountId) {
        return cartRepository.findAll()
                .filter(cart -> cart.accountId().equals(accountId))
                .next()
                .map(cart -> {
                    double total = 0;
                    for (int i = 0; i < cart.items().size(); i++) {
                        total += cart.items().get(i).quantity() * cart.items().get(i).productPrice();
                    }
                    return total;
                });
    }*/

    /*@Override
    public Mono<Cart> getCartByAccountId(String accountId) {
        return cartRepository.findAll().filter(cart -> cart.accountId().equals(accountId)).next();
    }*/

    /*@Override
    public Mono<String> getCartIdByAccountId(String accountId) {
        return cartRepository.findAll().filter(cart -> cart.accountId().equals(accountId)).next().map(Cart::cartId);
    }*/
}
