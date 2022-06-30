package com.micropos.orders.service;

import com.micropos.api.dto.CartDto;
import com.micropos.orders.model.Order;
import com.micropos.orders.model.Item;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OrderService {

    Mono<Order> newOrder();

    Mono<Order> newOrder(CartDto cartDto);

    /*Double checkout(Order order);

    Double checkout(Integer orderId);
*/
    Mono<Order> add(String orderId, Item item);

    //Order modify(Order order, Item item);

    /**
     * send Order to Delivery
     * @param order
     * @return
     */
    void generateDelivery(Order order);


    Flux<Order> getAllOrders();

/*    Order addOrder(Order order);*/

    Mono<Order> getOrder(String orderId);

   /* Integer test();

    Order delete(Order order, String productId); // delete an item in the order by productId

    Order empty(Order order);//*/
}
