package com.org.shopingKart.controller;

import com.org.shopingKart.Service.productService;
import com.org.shopingKart.entity.Product;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class productController {

    @Autowired
    private productService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAll());
    }


    @GetMapping("{prodId}")
    public ResponseEntity<Product> getProductById(@PathVariable("prodId") ObjectId prodId) {
        Product product = productService.getById(prodId);
        return product == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity<Product> saveProduct(@RequestBody Product product) {
        Product prod = productService.save(product);
        return prod == null ? ResponseEntity.noContent().build() : ResponseEntity.ok(prod);
    }

    @PutMapping("{prodId}")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product, @PathVariable("prodId") ObjectId prodId) {
        Product prod = productService.update(product, prodId);
        return prod == null ? ResponseEntity.noContent().build() : ResponseEntity.ok(prod);
    }

    @DeleteMapping
    public ResponseEntity<Boolean> removeProduct(@PathVariable("prodId") ObjectId prodId) {
        return productService.delete(prodId) ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : ResponseEntity.ok(true);
    }

}
