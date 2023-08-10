package cap.s42academy.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Transactions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

   // @ManyToOne
   // @JoinColumn(name = "report_id")
   // private Reports report;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Products product;

    @ManyToOne
    @JoinColumn(name = "coin_id")
    private Coins coin;

    private LocalDateTime transactionTime;
    private BigDecimal amountPaid;
    private BigDecimal changeReturned;
    private boolean canceled;


}
