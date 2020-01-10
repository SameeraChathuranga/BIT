package lk.PremasiriBrothers.inventorymanagement.asset.item.entity;


import lk.PremasiriBrothers.inventorymanagement.asset.commonAsset.Enum.Category;
import lk.PremasiriBrothers.inventorymanagement.asset.commonAsset.Enum.Status;
import lk.PremasiriBrothers.inventorymanagement.asset.commonAsset.entity.SupplierItem;
import lk.PremasiriBrothers.inventorymanagement.asset.process.finance.entity.InvoiceQuantity;
import lk.PremasiriBrothers.inventorymanagement.asset.process.generalLedger.entity.Ledger;
import lk.PremasiriBrothers.inventorymanagement.asset.process.goodReceivingManagement.entity.GrnQuantity;
import lk.PremasiriBrothers.inventorymanagement.asset.process.purchaseOrder.entity.ItemQuantity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Item {


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    @Column(unique = true)
    @NotNull(message = "This code is already add or enter incorrectly")
    private String code;

    private String description;


    @Enumerated(EnumType.STRING)
    private Category category;

    @OneToMany(mappedBy = "item")
    private List<SupplierItem> supplierItems;

    @OneToMany(mappedBy = "item")
    private List<Ledger> ledgers;

    @OneToMany(mappedBy = "item")
    private List<ItemQuantity> itemQuantities;

    @OneToMany(mappedBy = "item")
    private List<GrnQuantity> grnQuantities;

    @OneToMany(mappedBy = "item")
    private List<InvoiceQuantity> invoiceQuantities;

    @Enumerated(EnumType.STRING)
    private Status status;

    private BigDecimal cost;
    private BigDecimal selling;
    private int soh;
    private int reorderLimit;

    private LocalDate updatedAt;

    private LocalDate createdAt;

}
