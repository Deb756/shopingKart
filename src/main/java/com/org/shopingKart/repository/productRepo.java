package com.org.shopingKart.repository;

import com.org.shopingKart.entity.Product;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface productRepo  extends MongoRepository<Product, ObjectId> {
    List<Product> findAllByCategory(String Categoty);
}
