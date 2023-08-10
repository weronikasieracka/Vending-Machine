package cap.s42academy.service;

import cap.s42academy.model.Products;
import cap.s42academy.repository.ProductsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductsRepository productsRepository;


    public Products addOrUpdateProduct(Products product) {
        Products existingProduct = productsRepository.findByName(product.getName());

        if (existingProduct != null) {
            existingProduct.setQuantityAvailable(existingProduct.getQuantityAvailable() + product.getQuantityAvailable());
            return productsRepository.save(existingProduct);
        }

        return productsRepository.save(product);
    }

    public List<Products> getAllProducts() {
        return productsRepository.findAll();
    }

    public void decreaseQuantityAndDeleteIfZero(String productName, int amount) {
        Products product = productsRepository.findByName(productName);

        if (product == null) {
            throw new IllegalArgumentException("Product not found");
        }

        if (product.getQuantityAvailable() >= amount) {
            product.setQuantityAvailable(product.getQuantityAvailable() - amount);
        } else {
            throw new IllegalArgumentException("Not enough quantity available.");
        }

        if (product.getQuantityAvailable() == 0) {
            productsRepository.delete(product);
        } else {
            productsRepository.save(product);
        }
    }
    public int checkStockForProduct(String productName) {
        Products product = productsRepository.findByName(productName);

        if (product == null) {
            throw new IllegalArgumentException("Product not found");
        }

        return product.getQuantityAvailable();
    }
    public void replenishStockIfThresholdReached(String productName) {
        Products product = productsRepository.findByName(productName);

        if (product == null) {
            throw new IllegalArgumentException("Product not found");
        }

        if (product.getQuantityAvailable() <= product.getThreshold()) {
            int replenishAmount = 5;
            product.setQuantityAvailable(product.getQuantityAvailable() + replenishAmount);
            productsRepository.save(product);
        } else {
            throw new IllegalArgumentException("Threshold not reached for product " + productName);
        }

    }

        public Products getProductByName(String name) {
        return productsRepository.findByName(name);
    }
}





