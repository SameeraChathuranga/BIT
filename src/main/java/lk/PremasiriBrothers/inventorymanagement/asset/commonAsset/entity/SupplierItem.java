package lk.PremasiriBrothers.inventorymanagement.asset.commonAsset.entity;


import lk.PremasiriBrothers.inventorymanagement.asset.item.entity.Item;
import lk.PremasiriBrothers.inventorymanagement.asset.suppliers.entity.Supplier;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
public class SupplierItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Supplier supplier;

    @ManyToOne
    private Item item;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public SupplierItem(Integer id, Supplier supplier, Item item) {
        this.id = id;
        this.supplier = supplier;
        this.item = item;
    }

    public SupplierItem() {
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof SupplierItem))
            return false;
        final SupplierItem other = (SupplierItem) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.id;
        final Object other$id = other.id;
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$supplier = this.supplier;
        final Object other$supplier = other.supplier;
        if (this$supplier == null ? other$supplier != null : !this$supplier.equals(other$supplier)) return false;
        final Object this$item = this.item;
        final Object other$item = other.item;
        if (this$item == null ? other$item != null : !this$item.equals(other$item)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof SupplierItem;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.id;
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $supplier = this.supplier;
        result = result * PRIME + ($supplier == null ? 43 : $supplier.hashCode());
        final Object $item = this.item;
        result = result * PRIME + ($item == null ? 43 : $item.hashCode());
        return result;
    }
}
