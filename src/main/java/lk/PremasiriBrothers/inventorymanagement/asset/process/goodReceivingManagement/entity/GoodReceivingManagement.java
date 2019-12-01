package lk.PremasiriBrothers.inventorymanagement.asset.process.goodReceivingManagement.entity;


import lk.PremasiriBrothers.inventorymanagement.asset.item.entity.Item;
import lk.PremasiriBrothers.inventorymanagement.asset.process.goodReceivingManagement.entity.Enum.GRNStatus;
import lk.PremasiriBrothers.inventorymanagement.asset.process.purchaseOrder.entity.PurchaseOrder;
import lk.PremasiriBrothers.inventorymanagement.util.audit.AuditEntity;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class GoodReceivingManagement extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String code;

    @Enumerated(EnumType.STRING)
    private GRNStatus grnStatus;

    @ManyToOne(cascade = CascadeType.ALL)
    private Item item;

    private String quantity;

    @Column(unique = true)
    private BigDecimal selPrice;

    @Column(unique = true)
    private BigDecimal cost;

    private LocalDate createdDate;

    @ManyToOne(cascade = CascadeType.ALL)
    private PurchaseOrder purchaseOrder;

}



