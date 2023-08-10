package cap.s42academy.integration;

import cap.s42academy.repository.ProductsRepository;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import cap.s42academy.model.Products;

import org.junit.jupiter.api.Test;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureMockMvc
public class ProductServiceTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ProductsRepository productsRepository;

    @Test
    public void testAddOrUpdateProduct() {
        Products inputProduct = new Products();
        inputProduct.setName("TestProduct");
        inputProduct.setQuantityAvailable(20);

        ResponseEntity<Products> response = restTemplate.postForEntity("/addOrUpdateProduct", inputProduct, Products.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("TestProduct", response.getBody().getName());
        assertEquals(20, response.getBody().getQuantityAvailable());


        Products savedProduct = productsRepository.findByName("TestProduct");
        assertNotNull(savedProduct);
        assertEquals("TestProduct", savedProduct.getName());
        assertEquals(20, savedProduct.getQuantityAvailable());
    }
}
