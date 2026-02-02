package com.org.shopingKart.Service;

import com.org.shopingKart.entity.Product;
import com.org.shopingKart.repository.productRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
public class productService {

    @Autowired
    private productRepo productRepo;

    public List<Product> getAll() {
        return productRepo.findAll();
    }


    public Product save(Product product, MultipartFile imageFile) {
        String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
        Path uploadPath = Paths.get("uploads/products");
        try {
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
//            setting image path in product
            product.setImage("/uploads/products/" + fileName);
        } catch (IOException e) {
            throw new RuntimeException("Failed to store image", e);
        }

//        discount calculation
        double discountAmount = product.getPrice() * (product.getDiscount() / 100.0);
        product.setDiscountPrice(product.getPrice() - discountAmount);
        return productRepo.save(product);
    }

    public Product getById(ObjectId id) {
        return productRepo.findById(id).orElse(null);
    }

    public Product update(Product product, ObjectId id, MultipartFile imageFile) {
        Product existingProduct = productRepo.findById(id).orElse(null);
        if (existingProduct == null) return null;

        if (product.getTitle() != null)
            existingProduct.setTitle(product.getTitle());

        if (product.getDescription() != null)
            existingProduct.setDescription(product.getDescription());

        if (product.getCategory() != null)
            existingProduct.setCategory(product.getCategory());

        if (product.getStock() != null)
            existingProduct.setStock(product.getStock());

        if (product.getPrice() != null)
            existingProduct.setPrice(product.getPrice());

        if (product.getDiscount() != null)
            existingProduct.setDiscount(product.getDiscount());

        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                String filename = imageFile.getOriginalFilename();
                Files.copy(imageFile.getInputStream(), Paths.get("uploads/products" + filename), StandardCopyOption.REPLACE_EXISTING);
                existingProduct.setImage("/uploads/products/" + filename);

            } catch (IOException e) {
                throw new RuntimeException("Failed to update image", e);
            }
        }

        if (product.getPrice() != null || product.getDiscount() != null) {
            double price = existingProduct.getPrice();
            int discount = existingProduct.getDiscount() != null ? existingProduct.getDiscount() : 0;

            double discountPrice = Math.round(price * (1 - discount / 100.0) * 100) / 100.0;
            existingProduct.setDiscountPrice(discountPrice);
        }
        return productRepo.save(existingProduct);
    }


    public Boolean delete(ObjectId id) {
        if (!productRepo.existsById(id)) return false;
        productRepo.deleteById(id);
        return true;
    }

    public List<Product> getProductsByCategory(String category) {
        return productRepo.findAllByCategory(category);
    }

    public Product updateStock(ObjectId productId, Integer newStock) {
        if (newStock == null) return null;

        Product prod = productRepo.findById(productId).orElse(null);
        if (prod == null) return null;

        int currentStock = prod.getStock() != null ? prod.getStock() : 0;
        int updateStock = currentStock + newStock;

        if (newStock < 0) {
            throw new IllegalArgumentException("Stock cannot be negative");
        }

        prod.setStock(updateStock);
        return productRepo.save(prod);
    }

    public Product updateDiscount(ObjectId productId, Integer discount) {
        if (discount < 0 || discount > 100) {
            throw new IllegalArgumentException("Discount must be between 0 and 100");
        }
        Product prod = productRepo.findById(productId).orElse(null);
        if (prod == null) return null;
        prod.setDiscount(discount);
        if (prod.getPrice() != null) {
            double discountedPrice = prod.getPrice() * (1 - discount / 100.0);
            discountedPrice = Math.round(discountedPrice * 100.0) / 100.0;
            prod.setDiscountPrice(discountedPrice);
        }
        return productRepo.save(prod);
    }


}
