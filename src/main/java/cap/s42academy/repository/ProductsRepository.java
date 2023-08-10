package cap.s42academy.repository;


import cap.s42academy.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductsRepository extends JpaRepository<Products, Long> {
    @Modifying
    @Query("UPDATE Products p SET p.quantityAvailable = p.quantityAvailable - :amount WHERE p.id = :productId")
    int decreaseQuantityById(@Param("productId") Long productId, @Param("amount") int amount);

    Products findByName(String name);
}
