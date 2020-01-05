package lk.PremasiriBrothers.inventorymanagement.asset.process.purchaseOrder.entity;

import lk.PremasiriBrothers.inventorymanagement.asset.item.entity.Item;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ItemQuantity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int quantity;

    @ManyToOne
    private PurchaseOrder purchaseOrder;

    @ManyToOne
    private Item item;

//    @Transient
    private BigDecimal amount;
}
