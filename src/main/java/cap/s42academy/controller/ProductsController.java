package cap.s42academy.controller;

import cap.s42academy.model.Products;
import cap.s42academy.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping("products")
@RequiredArgsConstructor
public class ProductsController {

    private final ProductService productService;




    @PostMapping("/addOrUpdateProduct")
    public ResponseEntity<?> addOrUpdateProduct(@Valid @RequestBody Products product) {
        try {
            Products addedProduct = productService.addOrUpdateProduct(product);
            return ResponseEntity.ok(addedProduct);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Product with the same name already exists.");
        }
    }


    @GetMapping("/viewProducts")
    public ResponseEntity<List<Products>> getAllProducts() {
        List<Products> productsList = productService.getAllProducts();
        return ResponseEntity.ok(productsList);
    }
    @DeleteMapping("/removeProduct/{productName}/{amount}")
    public ResponseEntity<String> removeProducts(@PathVariable String productName, @PathVariable int amount) {
        try {
            productService.decreaseQuantityAndDeleteIfZero(productName, amount);
            return ResponseEntity.ok("Product(s) removed successfully.");
        } catch (IllegalArgumentException e) {
            if (e.getMessage().equals("Product not found")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
            } else if (e.getMessage().equals("Not enough quantity available.")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not enough quantity available.");
            } else {
                return ResponseEntity.badRequest().body("Invalid request.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
        }
    }

    @PostMapping("/purchaseProductByName")
    public ResponseEntity<?> purchaseProductByName(@RequestParam String productName, @RequestParam int quantity) {
        try {
            Products product = productService.getProductByName(productName);
            if (product == null) {
                return ResponseEntity.badRequest().body("Product not found.");
            }

            if (quantity <= 0 || quantity > product.getQuantityAvailable()) {
                return ResponseEntity.badRequest().body("Invalid quantity.");
            }

            return ResponseEntity.ok("Purchase successful.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing your request.");
        }
    }
    @GetMapping("/checkStock/{productName}")
    public ResponseEntity<String> checkStockForProduct(@PathVariable String productName) {
        try {
            int stock = productService.checkStockForProduct(productName);
            return ResponseEntity.ok("Available stock for product " + productName + ": " + stock);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
    }
    @PostMapping("/replenishStock/{productName}")
    public ResponseEntity<String> replenishStock(@PathVariable String productName) {
        try {
            productService.replenishStockIfThresholdReached(productName);
            return ResponseEntity.ok("Stock replenished successfully for product " + productName);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}

