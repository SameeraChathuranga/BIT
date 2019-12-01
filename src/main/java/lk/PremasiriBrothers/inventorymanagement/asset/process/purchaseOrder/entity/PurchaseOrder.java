package lk.PremasiriBrothers.inventorymanagement.asset.process.purchaseOrder.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lk.PremasiriBrothers.inventorymanagement.asset.process.goodReceivingManagement.entity.GoodReceivingManagement;
import lk.PremasiriBrothers.inventorymanagement.asset.process.purchaseOrder.entity.Enum.PurchaseOrderStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(value = {"updatedDate","remarks"}, allowGetters = true)
public class PurchaseOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String code;

    @Enumerated(EnumType.STRING)
    private PurchaseOrderStatus purchaseOrderStatus;

    private LocalDate createdDate;

    private LocalDate expectedDate;

    private LocalDate updatedDate;

    private String remarks;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "purchaseOrder")
    private List<ItemQuantity> itemQuantity;

    @OneToMany(mappedBy = "purchaseOrder")
    private List<GoodReceivingManagement> goodReceivingManagements;

}
