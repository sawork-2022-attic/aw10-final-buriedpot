package com.micropos.products.repository;

import com.micropos.products.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductMySqlRepository extends CrudRepository<Product, Integer> {


}
