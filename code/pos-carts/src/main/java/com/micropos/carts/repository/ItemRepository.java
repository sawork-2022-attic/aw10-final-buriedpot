package com.micropos.carts.repository;

import com.micropos.carts.model.Item;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends ReactiveMongoRepository<Item, String> {
}
