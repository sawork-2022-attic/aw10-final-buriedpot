package com.micropos.delivery.service;

import com.micropos.delivery.model.Delivery;
import com.micropos.delivery.model.Item;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DeliveryService {



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
    Flux<Delivery> getAllDelivery();

/*    Cart addCart(Cart cart);*/

    /**
     * get Cart by cartId. may be optimized: use accountId as cartId
     * @param deliveryId
     * @return
     */
    Mono<Delivery> getDelivery(String deliveryId);

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
