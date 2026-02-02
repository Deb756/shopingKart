package com.org.shopingKart.controller;

import com.org.shopingKart.Service.productService;
import com.org.shopingKart.entity.Product;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin("*")
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

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Product> saveProduct(@RequestPart("product") Product product, @RequestPart("image") MultipartFile imageFile) {
        Product prod = productService.save(product, imageFile);
        return prod == null ? ResponseEntity.noContent().build() : ResponseEntity.ok(prod);
    }

    @PutMapping("{prodId}")
    public ResponseEntity<Product> updateProduct(@RequestPart("product") Product product, @PathVariable("prodId") ObjectId prodId, @RequestPart(value = "image", required = false) MultipartFile imageFile) {
        Product prod = productService.update(product, prodId, imageFile);
        return prod == null ? ResponseEntity.noContent().build() : ResponseEntity.ok(prod);
    }

    @DeleteMapping
    public ResponseEntity<Boolean> removeProduct(@PathVariable("prodId") ObjectId prodId) {
        return productService.delete(prodId) ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : ResponseEntity.ok(true);
    }

    @GetMapping("category/{categoryName}")
    public ResponseEntity<List<Product>> getAllProductByCategory(@PathVariable("categoryName") String category) {
        return ResponseEntity.ok(productService.getProductsByCategory(category));
    }

    @PutMapping("stock/{prodId}")
    public ResponseEntity<Product> updateStocks(@PathVariable("prodId") ObjectId prodId, @RequestParam Integer newStock) {
        Product product = productService.updateStock(prodId, newStock);
        return product == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(product);
    }

    @PutMapping("discount/{prodId}")
    public ResponseEntity<Product> updateDiscount(@PathVariable("prodId") ObjectId prodId, @RequestParam Integer discount) {
        Product product = productService.updateDiscount(prodId, discount);
        return product == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(product);
    }

}
