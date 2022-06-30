package com.micropos.products.rest;

import com.micropos.api.dto.ProductDto;
import com.micropos.products.mapper.ProductMapper;
import com.micropos.products.model.Product;
import com.micropos.products.service.ProductService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("products")
public class ProductController {

    private final ProductMapper productMapper;

    private final ProductService productService;


    @Autowired
    public ProductController(ProductService productService, ProductMapper productMapper) {
        this.productMapper = productMapper;
        this.productService = productService;
    }

    @GetMapping("")
    @HystrixCommand(fallbackMethod = "listProductsEmpty")
    public ResponseEntity<List<ProductDto>> listProducts(){
        List<ProductDto> productDtos = new ArrayList<>(productMapper.toProductsDto(this.productService.products()));
        if (productDtos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }

/*
    @PostMapping("/products")
    public ResponseEntity<List<ProductDto>> postListProducts(){
        List<ProductDto> products = new ArrayList<>(productMapper.toProductsDto(this.productService.products()));
        if (products.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }*/

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> showProductById(@PathVariable String productId) {
        Product product = this.productService.getProduct(productId);
        if (product != null) {
            return new ResponseEntity<>(productMapper.toProductDto(product), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<List<ProductDto>> listProductsEmpty(){
        List<ProductDto> productDtos = new ArrayList<>();
        return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }
}
