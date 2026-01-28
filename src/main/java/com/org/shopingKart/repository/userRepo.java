package com.org.shopingKart.repository;

import com.org.shopingKart.entity.Users;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface userRepo extends MongoRepository<Users, ObjectId> {
    Users findByName(String name);
}
