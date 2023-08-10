package cap.s42academy.unit;

import cap.s42academy.controller.ProductsController;
import cap.s42academy.model.Products;
import cap.s42academy.repository.ProductsRepository;
import cap.s42academy.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductsRepository productsRepository;
    @InjectMocks
    private ProductService productService;
    @InjectMocks
    private ProductsController productController;
    @Captor
    private ArgumentCaptor<Products> ProductCaptor;



//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.initMocks(this);
//        productService = new ProductService(productsRepository);
//    }

    @Test
    public void testAddOrUpdateProduct_ExistingProduct() {
        Products existingProduct = new Products();
        existingProduct.setName("ExistingProduct");
        existingProduct.setQuantityAvailable(10);

        Products inputProduct = new Products();
        inputProduct.setName("ExistingProduct");
        inputProduct.setQuantityAvailable(5);

        when(productsRepository.findByName("ExistingProduct")).thenReturn(existingProduct);

        Products updatedProduct = productService.addOrUpdateProduct(inputProduct);



        verify(productsRepository, Mockito.times(1)).save(ProductCaptor.capture());
        assertEquals(15, ProductCaptor.getValue().getQuantityAvailable());
    }


    @Test
    public void testAddOrUpdateProduct_NewProduct() {
        Products inputProduct = new Products();
        inputProduct.setName("NewProduct");
        inputProduct.setQuantityAvailable(5);

        Products addedProduct = productService.addOrUpdateProduct(inputProduct);

        verify(productsRepository, Mockito.times(1)).save(ProductCaptor.capture());
        assertEquals("NewProduct", ProductCaptor.getValue().getName());
        assertEquals(5, ProductCaptor.getValue().getQuantityAvailable());
    }

    @Test
    public void testDecreaseQuantityAndDeleteIfZero_Success() {
        Products product = new Products();
        product.setName("ProductA");
        product.setQuantityAvailable(5);

        when(productsRepository.findByName("ProductA")).thenReturn(product);

        productService.decreaseQuantityAndDeleteIfZero("ProductA", 2);

        assertEquals(3, product.getQuantityAvailable());
        verify(productsRepository, times(1)).save(product);
    }

    @Test
    public void testDecreaseQuantityAndDeleteIfZero_ProductNotFound() {
        when(productsRepository.findByName("ProductA")).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> {
            productService.decreaseQuantityAndDeleteIfZero("ProductA", 2);
        });
    }

    @Test
    public void testDecreaseQuantityAndDeleteIfZero_NotEnoughQuantity() {
        Products product = new Products();
        product.setName("ProductA");
        product.setQuantityAvailable(1);

        when(productsRepository.findByName("ProductA")).thenReturn(product);

        assertThrows(IllegalArgumentException.class, () -> {
            productService.decreaseQuantityAndDeleteIfZero("ProductA", 2);
        });
    }

    @Test
    public void testDecreaseQuantityAndDeleteIfZero_QuantityZero() {
        Products product = new Products();
        product.setName("ProductA");
        product.setQuantityAvailable(2);

        when(productsRepository.findByName("ProductA")).thenReturn(product);

        productService.decreaseQuantityAndDeleteIfZero("ProductA", 2);

        verify(productsRepository, times(1)).delete(product);
    }

    @Test
    public void testCheckStockForProduct_ProductFound() {
        Products product = new Products();
        product.setName("ProductA");
        product.setQuantityAvailable(5);

        when(productsRepository.findByName("ProductA")).thenReturn(product);

        int stock = productService.checkStockForProduct("ProductA");

        assertEquals(5, stock);
    }

    @Test
    public void testCheckStockForProduct_ProductNotFound() {
        when(productsRepository.findByName("ProductB")).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> {
            productService.checkStockForProduct("ProductB");
        });
    }


}
