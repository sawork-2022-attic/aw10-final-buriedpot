package com.micropos.products.service;

import com.micropos.products.model.Product;
import com.micropos.products.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@CacheConfig(cacheNames = "products")
@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    public ProductServiceImpl(@Autowired ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Cacheable(cacheNames = "products")
    public List<Product> products() {
        return productRepository.allProducts();
    }

    @Override
    public Product getProduct(String id) {
        return productRepository.findProduct(id);
    }

    @Override
    public Product randomProduct() {
        return null;
    }
}
