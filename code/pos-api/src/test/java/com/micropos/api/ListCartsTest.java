package com.micropos.api;


import com.micropos.api.feign.CartFeignClient;
import com.micropos.api.reactivefeign.CartReactiveFeignClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactivefeign.spring.config.EnableReactiveFeignClients;

@SpringBootTest(classes = {ApiApplication.class})
@RunWith(SpringRunner.class)
public class ListCartsTest {
    @Autowired
    CartFeignClient cartFeignClient;
    /*@Autowired
    CartReactiveFeignClient cartReactiveFeignClient;
*/
    @Test
    public void testListCarts() {
        cartFeignClient.listCarts().subscribe(System.out::println);
        cartFeignClient.showCartById("62b726c1f9d85c57354882cd")
                .subscribe(System.out::println);
    }

}
