package com.micropos.orders.service;


import com.micropos.api.dto.CartDto;
import com.micropos.orders.mapper.ItemMapper;
import com.micropos.orders.mapper.OrderMapper;
import com.micropos.orders.model.Order;
import com.micropos.orders.model.Item;
import com.micropos.orders.repository.OrderRepository;
import com.micropos.orders.model.Order;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderMapper orderMapper;
    private ItemMapper itemMapper;
    private OrderRepository orderRepository;
    private RabbitTemplate rabbitTemplate;


    private final String COUNTER_URL = "http://POS-COUNTER/counter/";

    @LoadBalanced
    private RestTemplate restTemplate;

    @Autowired
    public OrderServiceImpl(OrderMapper orderMapper, ItemMapper itemMapper, OrderRepository orderRepository, RabbitTemplate rabbitTemplate, RestTemplate restTemplate) {
        this.orderMapper = orderMapper;
        this.itemMapper = itemMapper;
        this.orderRepository = orderRepository;
        this.rabbitTemplate = rabbitTemplate;
        this.restTemplate = restTemplate;
    }

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }



    @Autowired
    public void setOrderMapper(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }


    /*
        public Integer test() {

            Integer test = restTemplate.getForObject(COUNTER_URL + "/test", Integer.class);
            return test;
        }

        @Override
        public Double checkout(Integer orderId) {
            Optional<Order> order = this.orderRepository.findById(orderId);

            if (order.isEmpty()) return Double.valueOf(-1);

            return this.checkout(order.get());
        }

        @Override
        public Double checkout(Order order) {
            return null;
        }
    */
    @Override
    public Mono<Order> add(String orderId, Item item) {
        //itemRepository.save(item);
        return orderRepository.findById(orderId).flatMap(x -> {
            x.addItem(item);
            return orderRepository.save(x);
        });
    }

    @Override
    public Mono<Order> newOrder(CartDto cartDto) {
        Order order = new Order();
        order.orderId(null);
        order.items(new ArrayList<>(itemMapper.toItems(cartDto.items())));
        order.accountId(cartDto.cartId());
        return orderRepository.insert(order);
    }

    /*
    @Override
    public Order modify(Order order, Item item) {
        return null;
    }

    @Override
    public Order delete(Order order, String productId) {
        order.deleteItem(productId);
        return order;
    }

    @Override
    public Order empty(Order order) {
        order.empty();
        return order;
    }*/


    @Override
    public Flux<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Mono<Order> getOrder(String orderId) {
        return orderRepository.findById(orderId);
    }
/*
    @Override
    public Order addOrder(Order order) {
        return orderRepository.save(order);
    }*/

    @Override
    @Deprecated
    public Mono<Order> newOrder() {
        Order order = new Order();
        order.orderId(null);
        return orderRepository.insert(order);
    }

    @Override
    public void generateDelivery(Order order) {

        rabbitTemplate.convertAndSend("delivery", "order_delivery", orderMapper.toOrderDto(order));
    }
}
