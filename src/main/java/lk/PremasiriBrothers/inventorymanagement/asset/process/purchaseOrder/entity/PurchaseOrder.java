package lk.PremasiriBrothers.inventorymanagement.asset.process.purchaseOrder.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lk.PremasiriBrothers.inventorymanagement.asset.process.goodReceivingManagement.entity.GoodReceivingManagement;
import lk.PremasiriBrothers.inventorymanagement.asset.process.purchaseOrder.entity.Enum.PurchaseOrderStatus;
import lk.PremasiriBrothers.inventorymanagement.asset.suppliers.entity.Supplier;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(value = {"updatedDate", "remarks"}, allowGetters = true)
public class PurchaseOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String code;

    @Enumerated(EnumType.STRING)
    private PurchaseOrderStatus purchaseOrderStatus;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate expectedDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate updatedDate;

    private String remarks;

    private BigDecimal total;

    @ManyToOne
    private Supplier supplier;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "purchaseOrder")
    private List<ItemQuantity> itemQuantity;

    @OneToMany(mappedBy = "purchaseOrder")
    private List<GoodReceivingManagement> goodReceivingManagements;

}
