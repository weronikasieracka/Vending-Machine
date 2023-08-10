package cap.s42academy.model;

import cap.s42academy.repository.ProductsRepository;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Data
public class Products {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String name;
    @Min(value = 1, message = "need zero or positive integer")
    @NotNull
    private BigDecimal price;
    @NotNull
    @Min(value = 1, message = "need zero or positive integer")
    private Integer quantityAvailable;
    @NotNull
    @Min(value = 1, message = "need zero or positive integer")
    private Integer threshold;

    public void decreaseQuantity(int amount) {
        if (quantityAvailable >= amount) {
            quantityAvailable -= amount;
        } else {
            throw new IllegalArgumentException("Not enough quantity available.");
        }
    }
}
