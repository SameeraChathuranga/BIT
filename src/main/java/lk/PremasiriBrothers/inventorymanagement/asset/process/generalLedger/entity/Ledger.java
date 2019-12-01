package lk.PremasiriBrothers.inventorymanagement.asset.process.generalLedger.entity;



import lk.PremasiriBrothers.inventorymanagement.asset.item.entity.Item;
import lk.PremasiriBrothers.inventorymanagement.asset.suppliers.entity.Supplier;
import lk.PremasiriBrothers.inventorymanagement.util.audit.AuditEntity;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Ledger extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String code;

    @Column(nullable = false)
    private Integer availableQuantity;

    @Column(nullable = false)
    private BigDecimal salePrice;

    @ManyToOne(fetch = FetchType.EAGER)
    private Item item;

    @ManyToOne(fetch = FetchType.EAGER)
    private Supplier supplier;


}
