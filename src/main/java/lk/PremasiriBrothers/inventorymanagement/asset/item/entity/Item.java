package lk.PremasiriBrothers.inventorymanagement.asset.item.entity;



import lk.PremasiriBrothers.inventorymanagement.asset.commonAsset.Enum.Category;
import lk.PremasiriBrothers.inventorymanagement.asset.commonAsset.Enum.Status;
import lk.PremasiriBrothers.inventorymanagement.asset.commonAsset.entity.SupplierItem;
import lk.PremasiriBrothers.inventorymanagement.asset.process.generalLedger.entity.Ledger;
import lk.PremasiriBrothers.inventorymanagement.asset.process.goodReceivingManagement.entity.GoodReceivingManagement;
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
@ToString
public class Item {


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    @Column(unique = true)
    @NotNull(message = "This code is already add or enter incorrectly")
    private String code;

    private String description;

    @NotNull
    private String generic;

    @Enumerated(EnumType.STRING)
    private Category category;

    @OneToMany(mappedBy = "item")
    private List<SupplierItem> supplierItems;

    @OneToMany(mappedBy = "item")
    private List<Ledger> ledgers;


    @OneToMany(mappedBy = "item")
    private List<GoodReceivingManagement> goodReceivingManagements;

    @Enumerated(EnumType.STRING)
    private Status status;

    private BigDecimal cost;
    private BigDecimal selling;
    private String soh;
    private int reorderLimit;

    private LocalDate updatedAt;

    private LocalDate createdAt;

}
