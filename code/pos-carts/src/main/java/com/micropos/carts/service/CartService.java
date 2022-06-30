package com.micropos.carts.service;

import com.micropos.api.dto.CartDto;
import com.micropos.carts.model.Cart;
import com.micropos.carts.model.Item;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

public interface CartService {

    /**
     * create an empty cart
     * @return
     */
    @Deprecated
    Mono<Cart> newCart();

    /**
     * create an empty cart
     * @return
     */
    @Deprecated
    Mono<Cart> newCart(String accountId);

    /*Double checkout(Cart cart);
*/

    /**
     * checkout. calculate the money and delete it from repo.
     * @param cartId
     * @return
     */
    Mono<Double> checkout(String cartId);


    /**
     * add an item into cart
     * @param cartId
     * @param item
     * @return
     */
    Mono<Cart> add(String cartId, Item item);

    //Cart modify(Cart cart, Item item);

    /**
     * accountId map to only one accountId. So can checkout by accountId
     * @param
     * @return
     */
    //Mono<Double> checkoutByAccoundId(String accountId);


    /**
     * get all carts
     * @return
     */
    Flux<Cart> getAllCarts();

/*    Cart addCart(Cart cart);*/

    /**
     * get Cart by cartId. may be optimized: use accountId as cartId
     * @param cartId
     * @return
     */
    Mono<Cart> getCart(String cartId);

    /**
     * get Cart by accountId. may be optimized
     * @param
     * @return
     */
    //Mono<Cart> getCartByAccountId(String accountId);

    /**
     * get CartId by accountId.
     * @param
     * @return
     */
    //Mono<String> getCartIdByAccountId(String accountId);

   /* Integer test();

    Cart delete(Cart cart, String productId); // delete an item in the cart by productId

    Cart empty(Cart cart);//*/
}
