[TOC]

# 测试和评价

姓名：郭睿杰

学号：MG21330024

院系：计算机科学与技术





## 目的

* 测试是否为响应式架构（是否满足响应式特点）；
* 测试是否easily broken down；
* 测试是否易扩展；
* 测试是否为消息驱动。



## 响应式架构

### 测试方法1

通过 `http://localhost:8080/carts/newcart/{accountId}`创建多个购物车，通过 `/carts` 对购物车数据库进行查询操作。首先看核心代码

```java
@GetMapping(value = "", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
public Flux<CartDto> streamListCarts() {
    return cartService.getAllCarts().map(cart -> cartMapper.toCartDto(cart));
}
```

按流的方式返回购物车：

![image-20220630154250112](md_imgs\image-20220630154250112.png)



### 测试方法2

对一个购物车进行结账操作。在Postman中，GET请求 `http://localhost:8080/carts/checkout/guoruijie`

由于结账操作也是以流的方式去完成（核心代码如下，见 `pos-carts` 模块 `com.micropos.carts.service.CartServiceImpl）：

```java
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
```

打开断点调试，会发现，当结账结果最终返回用户界面时，checkout当中的代码才会被执行。具体测试如下：

* 我们先往用户购物车中添加一定的物品（可以看到，我们使用POST请求来添加，这是复合RESTFUL架构的）：

  ![image-20220630154832716](md_imgs\image-20220630154832716.png)

![image-20220630154840431](md_imgs\image-20220630154840431.png)

然后，checkout（url为 `http://localhost:8080/carts/checkout/guoruijie`）

![image-20220630154927655](md_imgs\image-20220630154927655.png)

![image-20220630154950983](md_imgs\image-20220630154950983.png)



## Not easily broken down

本实验通过熔断机制来保证系统的错误不会传播。

测试：建立空数据库，由`http://localhost:8080/products`查询。返回NOT_FOUND。



## 易扩展

易扩展性从架构风格即可观察。

### 代码易扩展

1. 如，用户信息当前存储只有用户名，因此完全可以使用String类型。但仍然封装在pos-api model中的Account类中。

### 系统易扩展

不同服务直接几乎无耦合，通过api模块中的DTO进行数据通信，扩展性好。



## 消息驱动通信

在 `pos-orders` 和 `pos-delivery` 模块通过**rabbitmq**进行消息通信。

实验步骤：

* 启动注册中心、网关、`pos-delivery` 服务。

* 运行 `pos-orders` 模块中本人已写好的测试代码：`com.micropos.orders.OrdersApplicationTest`。

  ```java
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
  ```

* 观察到 `pos-delivery` 含有 `@RabbitListener` 注解的方法打印出如下消息：

  ![image-20220630160222976](md_imgs\image-20220630160222976.png)

* 消息驱动工作正常。





## 集成作业6数据库

![img](C:\Works\javaworks\software_architecture\aw06-buriedpot\img.png)