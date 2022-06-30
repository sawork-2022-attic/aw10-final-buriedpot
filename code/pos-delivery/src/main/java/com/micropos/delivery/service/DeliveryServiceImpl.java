package com.micropos.delivery.service;


import com.micropos.delivery.model.Delivery;
import com.micropos.delivery.model.Item;
import com.micropos.delivery.repository.DeliveryRepository;
import com.micropos.delivery.mapper.ItemMapper;
import com.micropos.delivery.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DeliveryServiceImpl implements DeliveryService {

    private DeliveryRepository deliveryRepository;
    private OrderMapper orderMapper;
    private ItemMapper itemMapper;


    private final String COUNTER_URL = "http://POS-COUNTER/counter/";

    @LoadBalanced
    private RestTemplate restTemplate;

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Autowired
    public void setItemRepository(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    @Autowired
    public void setOrderMapper(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
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
    public Flux<Delivery> getAllDelivery() {
        return deliveryRepository.findAll();
    }

    @Override
    public Mono<Delivery> getDelivery(String deliveryId) {
        return deliveryRepository.findById(deliveryId);
    }
/*
    @Override
    public Cart addCart(Cart cart) {
        return cartRepository.save(cart);
    }*/
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
