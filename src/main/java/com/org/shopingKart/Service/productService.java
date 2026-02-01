package com.org.shopingKart.Service;

import com.org.shopingKart.entity.Product;
import com.org.shopingKart.repository.productRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class productService {

    @Autowired
    private productRepo productRepo;

    public List<Product> getAll() {
        return productRepo.findAll();
    }

    public Product save(Product product) {
        double discountAmount =
                product.getPrice() * (product.getDiscount() / 100.0);

        product.setDiscountPrice(product.getPrice() - discountAmount);

        return productRepo.save(product);
    }

    public Product getById(ObjectId id) {
        return productRepo.findById(id).orElse(null);
    }

    public Product update(Product product, ObjectId id) {

        Product existingProduct = productRepo.findById(id).orElse(null);
        if (existingProduct == null) return null;

        if (product.getTitle() != null)
            existingProduct.setTitle(product.getTitle());

        if (product.getDescription() != null)
            existingProduct.setDescription(product.getDescription());

        if (product.getCategory() != null)
            existingProduct.setCategory(product.getCategory());

        if (product.getPrice() != null)
            existingProduct.setPrice(product.getPrice());

        if (product.getDiscount() != null)
            existingProduct.setDiscount(product.getDiscount());

        if (product.getPrice() != null) {
            double discountPrice =
                    Math.round(
                            product.getPrice() *
                                    (1 - product.getDiscount() / 100.0) * 100
                    ) / 100.0;

            existingProduct.setDiscountPrice(discountPrice);
        }
        // Recalculate discount price if needed
        if (product.getDiscount() != null) {
            double discountPrice = Math.round(existingProduct.getPrice() * (1 - product.getDiscount() / 100.0) * 100) / 100.0;

            existingProduct.setDiscountPrice(discountPrice);
        }

        return productRepo.save(existingProduct);
    }

    public Boolean delete(ObjectId id) {
        if (!productRepo.existsById(id)) return false;
        productRepo.deleteById(id);
        return true;
    }

}
