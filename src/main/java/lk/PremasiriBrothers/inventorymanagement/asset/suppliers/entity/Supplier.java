package lk.PremasiriBrothers.inventorymanagement.asset.suppliers.entity;



import lk.PremasiriBrothers.inventorymanagement.asset.commonAsset.entity.SupplierItem;
import lk.PremasiriBrothers.inventorymanagement.asset.process.goodReceivingManagement.entity.GoodReceivingManagement;
import lk.PremasiriBrothers.inventorymanagement.asset.process.purchaseOrder.entity.PurchaseOrder;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String code;

    @Column(unique = true)
    @Size(min = 4, message = "Provide valid name")
    private String name;

    @Size(min = 10, message = "Provide valid name")
    private String address;

    @Size(min = 10, message = "Provide valid phone number")
    private String land;

    @Email(message = "Provide valid email")
    private String email;

    private String contactName;

    @Transient
    private List<Integer> ids;

    @OneToMany(mappedBy = "supplier")
    private List<SupplierItem> supplierItems = new ArrayList<>();

    @Size(min = 10, message = "Provide valid phone number")
    private String contactMobile;

    @Email(message = "Provide valid email")
    private String contactEmail;

    private LocalDate updatedAt;

    private LocalDate createdAt;

    @OneToMany(mappedBy = "supplier")
    private List<PurchaseOrder> purchaseOrders;

    @OneToMany(mappedBy = "supplier")
    private List<GoodReceivingManagement> goodReceivingManagements;
}
