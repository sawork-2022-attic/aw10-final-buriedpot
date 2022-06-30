package com.micropos.delivery.repository;


import com.micropos.delivery.model.Delivery;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRepository extends ReactiveMongoRepository<Delivery, String> {
}
