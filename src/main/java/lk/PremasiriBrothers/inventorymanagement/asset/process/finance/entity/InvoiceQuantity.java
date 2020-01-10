package lk.PremasiriBrothers.inventorymanagement.asset.process.finance.entity;

import lk.PremasiriBrothers.inventorymanagement.asset.item.entity.Item;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceQuantity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int quantity;

    private BigDecimal amount;

    @ManyToOne
    private Item item;

    @ManyToOne
    private Invoice invoice;
}
