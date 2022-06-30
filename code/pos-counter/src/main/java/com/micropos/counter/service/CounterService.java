package com.micropos.counter.service;

import com.micropos.api.dto.CartDto;
import com.micropos.api.dto.ItemDto;
import org.springframework.stereotype.Service;

@Service
public class CounterService {

    public double getTotal(CartDto cart) {
        double total = 0;
        for (ItemDto item : cart.items()) {
            total += item.quantity() * item.productPrice();
        }
        return total;
    }
}
