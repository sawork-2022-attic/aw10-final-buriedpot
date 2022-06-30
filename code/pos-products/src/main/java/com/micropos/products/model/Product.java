package com.micropos.products.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product implements Serializable {
    private String id;
    private String name;
    private double price;
    private String image;

    @Override
    public String toString() {
        return getId() + "\t" + getName() + "\t" + getPrice();
    }
}
