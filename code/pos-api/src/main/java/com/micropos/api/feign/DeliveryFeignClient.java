package com.micropos.api.feign;

import com.micropos.api.dto.CartDto;
import com.micropos.api.dto.DeliveryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactivefeign.spring.config.ReactiveFeignClient;

import java.util.List;

@FeignClient(name = "posdelivery")
public interface DeliveryFeignClient {
    @GetMapping("/delivery")
    public ResponseEntity<List<DeliveryDto>> listDelivery();
    @GetMapping("/delivery/delivery/{deliveryId}")
    public ResponseEntity<CartDto> showCartById(@PathVariable(value = "cartId") Integer cartId);
}
