package com.micropos.orders.mapper;

import com.micropos.api.dto.OrderDto;
import com.micropos.orders.model.Order;
import org.mapstruct.Mapper;

import java.util.Collection;

@Mapper
public interface OrderMapper {
    Order toOrder(OrderDto orderDto);

    OrderDto toOrderDto(Order order);

    Collection<OrderDto> toOrdersDto(Collection<Order> orders);

    Collection<Order> toOrders(Collection<OrderDto> ordersDto);


}
