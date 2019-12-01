package lk.PremasiriBrothers.inventorymanagement.asset.process.purchaseOrder.entity;


import lk.PremasiriBrothers.inventorymanagement.asset.suppliers.entity.Supplier;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ItemQuantity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private PurchaseOrder purchaseOrder;

    private int quantity;

    @ManyToOne
    private Supplier supplier;
}
