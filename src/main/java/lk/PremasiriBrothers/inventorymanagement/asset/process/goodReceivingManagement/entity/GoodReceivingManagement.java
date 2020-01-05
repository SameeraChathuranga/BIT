package lk.PremasiriBrothers.inventorymanagement.asset.process.goodReceivingManagement.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lk.PremasiriBrothers.inventorymanagement.asset.process.purchaseOrder.entity.PurchaseOrder;
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
public class GoodReceivingManagement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String code;

    @ManyToOne
    private Supplier supplier;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate updatedDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate receivedDate;

    private String supplierInvoice;

    private String remarks;

    private BigDecimal total;

    @ManyToOne
    private PurchaseOrder purchaseOrder;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "goodReceivingManagement")
    private List<GrnQuantity> grnQuantities;

}



