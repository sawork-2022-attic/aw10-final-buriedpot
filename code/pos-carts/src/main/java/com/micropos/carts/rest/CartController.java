package com.micropos.carts.rest;


import com.micropos.api.dto.CartDto;
import com.micropos.api.dto.ItemDto;
import com.micropos.carts.mapper.CartMapper;
import com.micropos.carts.mapper.ItemMapper;
import com.micropos.carts.model.Cart;
import com.micropos.carts.model.Item;
import com.micropos.carts.service.CartService;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("carts")
public class CartController  {

    private CartService cartService;

    private CartMapper cartMapper;
    private ItemMapper itemMapper;

    public CartController(CartService cartService){
        this.cartService = cartService;
    }


    @Autowired
    public void setCartService(CartService cartService) {
        this.cartService = cartService;
    }

    @Autowired
    public void setItemMapper(ItemMapper itemMapper) {
        this.itemMapper = itemMapper;
    }

    @Autowired
    public CartController(CartService cartService, CartMapper cartMapper) {
        this.cartService = cartService;
        this.cartMapper = cartMapper;
    }


/*    @PostMapping("/cart/{cartId}")
    public Mono<ResponseEntity<CartDto>> addItemToCart(@PathVariable("cartId") String cartId, @RequestBody ItemDto itemDto) {
        Mono<Cart> cartMono = cartService.getCart(cartId);
        if(cartMono == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Cart ret = cartService.add(cart, item);
        if(ret == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(ret, HttpStatus.OK);
    }*/

    @GetMapping("/cart/{cartId}")
    public Mono<CartDto> showCartById(@PathVariable("cartId") String cartId) {

        /*return cartService.getCart(cartId).map(cart -> new ResponseEntity<>(cartMapper.toCartDto(cart), HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));*/
        return cartService.getCart(cartId).map(cartMapper::toCartDto);
    }
/*
    @DeleteMapping("/cart/{cartId}")
    public ResponseEntity<CartDto> deleteItemFromCart(@PathVariable("cartId") Integer cartId, @RequestParam String productId) {
        Cart cart = cartService.getCart(cartId).orElse(null);
        if(cart == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Cart ret = cartService.delete(cart, productId);
        if(ret == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(cartMapper.toCartDto(ret),HttpStatus.OK);
    }

    *//**
     * modify item num

     * @return
     *//*
    @PutMapping("/cart/{cartId}")
    public ResponseEntity<CartDto> modifyItemInCart(@PathVariable("cartId") int cartId, @RequestBody Item item) {
        Cart cart = cartService.getCart(cartId).orElse(null);
        if(cart == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Cart ret = cartService.add(cart, item);
        if(ret == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(cartMapper.toCartDto(ret), HttpStatus.OK);
    }*/

    //@Override
    @PostMapping("/cart/{cartId}")
    public Mono<CartDto> addItemToCart(@PathVariable("cartId") String cartId, @RequestBody ItemDto itemDto) {
        /*return cartService.add(cartId, itemMapper.toItem(itemDto)).map(cart -> new ResponseEntity<>(
                cartMapper.toCartDto(cart), HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));*/
        return cartService.add(cartId, itemMapper.toItem(itemDto)).map(cartMapper::toCartDto);
    }

    @GetMapping("/checkout/{cartId}")
    public Mono<Double> checkout(@PathVariable("cartId") String cartId) {
        /*return cartService.checkout(cartId).map(ret -> new ResponseEntity<>(ret, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));*/
        return cartService.checkout(cartId);
    }


    /*@GetMapping("/checkoutByAccountId/{accountId}")
    public Mono<ResponseEntity<Double>> checkoutByAccountId(@PathVariable("accountId") String accountId) {
        return cartService.checkoutByAccoundId(accountId).map(ret -> new ResponseEntity<>(ret, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }*/

/*
    @Override
    @PostMapping("/carts")
    public ResponseEntity<CartDto> createCart(@RequestBody CartDto cartDto) {
        Cart ret = cartMapper.toCart(cartDto);

        if(ret == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ret = cartService.addCart(ret);

        if (ret == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(cartMapper.toCartDto(ret),HttpStatus.OK);
    }
*/
    @GetMapping("/newcart")
    @Deprecated
    public Mono<CartDto> newCart() {

        Mono<Cart> ret = cartService.newCart();

        return ret.map(cart -> cartMapper.toCartDto(cart));
    }

    @GetMapping("/newcart/{accountId}")
    @Deprecated
    public Mono<CartDto> newCart(@PathVariable("accountId") String accountId) {

        Mono<Cart> ret = cartService.newCart(accountId);

        return ret.map(cart -> cartMapper.toCartDto(cart));
    }

    @GetMapping(value = "", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<CartDto> streamListCarts() {

        return cartService.getAllCarts().map(cart -> cartMapper.toCartDto(cart));
    }

    public Flux<CartDto> listCarts() {

        return cartService.getAllCarts().map(cart -> cartMapper.toCartDto(cart));
    }

/*
    @Override
    @GetMapping("/carts/{cartId}")
    public ResponseEntity<CartDto> showCartById(@PathVariable("cartId") Integer cartId) {

        Cart cart = cartService.getCart(cartId).orElse(null);
        if(cart == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        CartDto cartDto = cartMapper.toCartDto(cart);
        return new ResponseEntity<>(cartDto,HttpStatus.OK);
    }

    @Override
    @GetMapping("/carts/{cartId}/total")
    public ResponseEntity<Double> showCartTotal(@PathVariable("cartId") Integer cartId) {
        double totalAmount =  cartService.checkout(cartId);
        if(cartId == -1d)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(totalAmount);
    }

    //@Override
    @GetMapping("/carts/{cartId}/empty")
    public ResponseEntity<CartDto> emptyCart(@PathVariable("cartId") Integer cartId) {

        Cart cart = cartService.getCart(cartId).orElse(null);
        if(cart == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        cart.empty();
        CartDto cartDto = cartMapper.toCartDto(cart);
        return new ResponseEntity<>(cartDto,HttpStatus.OK);
    }

    @Override
    @PutMapping("/carts/empty")
    public ResponseEntity<CartDto> emptyCart(@RequestBody CartDto cartDto) {

        Cart cart = cartService.empty(cartMapper.toCart(cartDto));
        if(cart == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(cartMapper.toCartDto(cart));
    }*/

}
