package com.micropos.orders;

import com.micropos.orders.mapper.OrderMapper;
import com.micropos.orders.model.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = {OrdersApplication.class})
@RunWith(SpringRunner.class)
public class OrdersApplicationTest {


    @Autowired
    OrderMapper orderMapper;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testSend() {
        Order order = new Order();
        order.orderId("testId");
        order.accountId("testAccountId");

        rabbitTemplate.convertAndSend("delivery", "order_delivery", orderMapper.toOrderDto(order));
    }


}
