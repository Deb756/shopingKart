package com.org.shopingKart.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Document(collection = "userDetails")
public class Users {

    @Id
    private ObjectId id;

    @NonNull
    private String name;

    @NonNull
    private String mobileNumber;

    @Indexed(unique = true)
    private String email;

    private String address;

    private String city;

    private String state;

    private String pincode;

    @NonNull
    private String password;

    private String profileImage;

    private String role;

    @NonNull
    private Instant regDate;

}
