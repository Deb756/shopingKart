package com.org.shopingKart.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "productDetails")
public class Product {

    @Id
    private ObjectId id;

    @NonNull
    private String title;

    @NonNull
    private String description;

    @NonNull
    private String category;

    @NonNull
    private Double price;

    @NonNull
    private Integer stock;

    private String image;

    @NonNull
    private Integer discount;

    private Double discountPrice;

}
