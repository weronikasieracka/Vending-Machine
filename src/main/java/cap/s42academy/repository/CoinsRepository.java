package cap.s42academy.repository;

import cap.s42academy.model.Coins;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface CoinsRepository extends JpaRepository<Coins, Long> {
    Optional<Coins> findByCoinValue(Integer coinValue);



}
