package cap.s42academy.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Data
public class Coins {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long coinId;

    private Integer coinValue;

    private Integer quantityAvailable;

    public Coins(){}

    public Coins(Integer value, int i) {
        this.coinValue = value;
        this.quantityAvailable = i;
    }



}
