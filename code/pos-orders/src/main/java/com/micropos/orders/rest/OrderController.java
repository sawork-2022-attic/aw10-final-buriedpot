package com.micropos.orders.rest;

import com.micropos.api.dto.CartDto;
import com.micropos.api.dto.OrderDto;
import com.micropos.api.dto.ItemDto;
import com.micropos.orders.mapper.ItemMapper;
import com.micropos.orders.mapper.OrderMapper;
import com.micropos.orders.model.Order;
import com.micropos.orders.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("orders")
public class OrderController  {

    private OrderService orderService;

    private OrderMapper orderMapper;

    private ItemMapper itemMapper;


    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }


    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }



    @Autowired
    public OrderController(OrderService orderService, OrderMapper orderMapper) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }


/*    @PostMapping("/order/{orderId}")
    public Mono<ResponseEntity<OrderDto>> addItemToOrder(@PathVariable("orderId") String orderId, @RequestBody ItemDto itemDto) {
        Mono<Order> orderMono = orderService.getOrder(orderId);
        if(orderMono == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Order ret = orderService.add(order, item);
        if(ret == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(ret, HttpStatus.OK);
    }*/

    @GetMapping("/order/{orderId}")
    public Mono<ResponseEntity<OrderDto>> showOrderById(@PathVariable("orderId") String orderId) {

        return orderService.getOrder(orderId).map(order -> new ResponseEntity<>(orderMapper.toOrderDto(order), HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
/*
    @DeleteMapping("/order/{orderId}")
    public ResponseEntity<OrderDto> deleteItemFromOrder(@PathVariable("orderId") Integer orderId, @RequestParam String productId) {
        Order order = orderService.getOrder(orderId).orElse(null);
        if(order == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Order ret = orderService.delete(order, productId);
        if(ret == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(orderMapper.toOrderDto(ret),HttpStatus.OK);
    }

    *//**
     * modify item num

     * @return
     *//*
    @PutMapping("/order/{orderId}")
    public ResponseEntity<OrderDto> modifyItemInOrder(@PathVariable("orderId") int orderId, @RequestBody Item item) {
        Order order = orderService.getOrder(orderId).orElse(null);
        if(order == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Order ret = orderService.add(order, item);
        if(ret == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(orderMapper.toOrderDto(ret), HttpStatus.OK);
    }*/

    //@Override
    @PostMapping("/order/{orderId}")
    public Mono<ResponseEntity<OrderDto>> addItemToOrder(@PathVariable("orderId") String orderId, @RequestBody ItemDto itemDto) {
        return orderService.add(orderId, itemMapper.toItem(itemDto)).map(order -> new ResponseEntity<>(
                orderMapper.toOrderDto(order), HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }



/*
    @Override
    @PostMapping("/orders")
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto) {
        Order ret = orderMapper.toOrder(orderDto);

        if(ret == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ret = orderService.addOrder(ret);

        if (ret == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(orderMapper.toOrderDto(ret),HttpStatus.OK);
    }
*/


    /**
     * Account checkout, carts service send a new Order here
     * at same time, send a delivery message to DeliveryService!
     * @param cartDto
     * @return
     */
    @PostMapping("/neworder")
    public Mono<OrderDto> newOrder(@RequestBody CartDto cartDto) {

        Mono<Order> ret = orderService.newOrder(cartDto);
        return ret.flatMap(order -> {
            OrderDto orderDto = orderMapper.toOrderDto(order);
            orderService.generateDelivery(order); // send rabbitmq to delivery

            return Mono.just(order);
        }).map(order -> {
            return orderMapper.toOrderDto(order);
        });
    }

    @GetMapping(value = "", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<OrderDto> streamListOrders() {

        return orderService.getAllOrders().map(order -> orderMapper.toOrderDto(order));
    }

    public Flux<OrderDto> listOrders() {

        return orderService.getAllOrders().map(order -> orderMapper.toOrderDto(order));
    }

/*
    @Override
    @GetMapping("/orders/{orderId}")
    public ResponseEntity<OrderDto> showOrderById(@PathVariable("orderId") Integer orderId) {

        Order order = orderService.getOrder(orderId).orElse(null);
        if(order == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        OrderDto orderDto = orderMapper.toOrderDto(order);
        return new ResponseEntity<>(orderDto,HttpStatus.OK);
    }

    @Override
    @GetMapping("/orders/{orderId}/total")
    public ResponseEntity<Double> showOrderTotal(@PathVariable("orderId") Integer orderId) {
        double totalAmount =  orderService.checkout(orderId);
        if(orderId == -1d)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(totalAmount);
    }

    //@Override
    @GetMapping("/orders/{orderId}/empty")
    public ResponseEntity<OrderDto> emptyOrder(@PathVariable("orderId") Integer orderId) {

        Order order = orderService.getOrder(orderId).orElse(null);
        if(order == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        order.empty();
        OrderDto orderDto = orderMapper.toOrderDto(order);
        return new ResponseEntity<>(orderDto,HttpStatus.OK);
    }

    @Override
    @PutMapping("/orders/empty")
    public ResponseEntity<OrderDto> emptyOrder(@RequestBody OrderDto orderDto) {

        Order order = orderService.empty(orderMapper.toOrder(orderDto));
        if(order == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(orderMapper.toOrderDto(order));
    }*/

}
