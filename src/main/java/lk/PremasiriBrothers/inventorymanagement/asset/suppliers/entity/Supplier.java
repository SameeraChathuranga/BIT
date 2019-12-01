package lk.PremasiriBrothers.inventorymanagement.asset.suppliers.entity;


import lk.PremasiriBrothers.inventorymanagement.asset.commonAsset.entity.SupplierItem;
import lk.PremasiriBrothers.inventorymanagement.asset.process.generalLedger.entity.Ledger;
import lk.PremasiriBrothers.inventorymanagement.asset.process.purchaseOrder.entity.ItemQuantity;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ToString
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
    private List<Ledger> ledgers;


    @OneToMany(mappedBy = "supplier")
    private List<SupplierItem> supplierItems = new ArrayList<>();

    @OneToMany(mappedBy = "supplier")
    private List<ItemQuantity> itemQuantities;

    @Size(min = 10, message = "Provide valid phone number")
    private String contactMobile;

    @Email(message = "Provide valid email")
    private String contactEmail;

    private LocalDate updatedAt;

    private LocalDate createdAt;


    public Integer getId() {
        return this.id;
    }

    public String getCode() {
        return this.code;
    }

    public @Size(min = 4, message = "Provide valid name") String getName() {
        return this.name;
    }

    public @Size(min = 10, message = "Provide valid name") String getAddress() {
        return this.address;
    }

    public @Size(min = 10, message = "Provide valid phone number") String getLand() {
        return this.land;
    }

    public @Email(message = "Provide valid email") String getEmail() {
        return this.email;
    }

    public String getContactName() {
        return this.contactName;
    }

    public List<Integer> getIds() {
        return this.ids;
    }

    public List<Ledger> getLedgers() {
        return this.ledgers;
    }

    public List<SupplierItem> getSupplierItems() {
        return this.supplierItems;
    }

    public List<ItemQuantity> getItemQuantities() {
        return this.itemQuantities;
    }

    public @Size(min = 10, message = "Provide valid phone number") String getContactMobile() {
        return this.contactMobile;
    }

    public @Email(message = "Provide valid email") String getContactEmail() {
        return this.contactEmail;
    }

    public LocalDate getUpdatedAt() {
        return this.updatedAt;
    }

    public LocalDate getCreatedAt() {
        return this.createdAt;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(@Size(min = 4, message = "Provide valid name") String name) {
        this.name = name;
    }

    public void setAddress(@Size(min = 10, message = "Provide valid name") String address) {
        this.address = address;
    }

    public void setLand(@Size(min = 10, message = "Provide valid phone number") String land) {
        this.land = land;
    }

    public void setEmail(@Email(message = "Provide valid email") String email) {
        this.email = email;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    public void setLedgers(List<Ledger> ledgers) {
        this.ledgers = ledgers;
    }

    public void setSupplierItems(List<SupplierItem> supplierItems) {
        this.supplierItems = supplierItems;
    }

    public void setItemQuantities(List<ItemQuantity> itemQuantities) {
        this.itemQuantities = itemQuantities;
    }

    public void setContactMobile(@Size(min = 10, message = "Provide valid phone number") String contactMobile) {
        this.contactMobile = contactMobile;
    }

    public void setContactEmail(@Email(message = "Provide valid email") String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
}
