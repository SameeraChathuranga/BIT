package lk.PremasiriBrothers.inventorymanagement.asset.process.generalLedger.entity;


import lk.PremasiriBrothers.inventorymanagement.asset.item.entity.Item;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode()
public class Ledger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String code;

    @Column(nullable = false)
    private int availableQuantity;

    @Column(nullable = false)
    private BigDecimal cost;

    @Column(nullable = false)
    private BigDecimal salePrice;

    @Column(nullable = false)
    private int reorderLimit;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate expireDate;

    @ManyToOne(fetch = FetchType.EAGER)
    private Item item;

    private LocalDate updatedAt;

    private LocalDate createdAt;

}
