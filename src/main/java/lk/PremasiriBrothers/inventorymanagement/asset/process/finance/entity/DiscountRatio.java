package lk.PremasiriBrothers.inventorymanagement.asset.process.finance.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class DiscountRatio {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Basic
    @Column(name = "name", nullable = false, length = 45)
    private String name;

    @Basic
    @Column(name = "amount", nullable = false, precision=10, scale=2)
    private BigDecimal amount;

/*    @OneToMany
    @JoinColumn(name = "discount_ratio_id")
    private List<Invoice> invoices = new ArrayList<>();*/

}
