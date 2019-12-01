package lk.PremasiriBrothers.inventorymanagement.asset.commonAsset.dao;

import lk.PremasiriBrothers.inventorymanagement.asset.commonAsset.entity.SupplierItem;
import lk.PremasiriBrothers.inventorymanagement.asset.item.entity.Item;
import lk.PremasiriBrothers.inventorymanagement.asset.suppliers.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierItemDao extends JpaRepository<SupplierItem, Integer> {
    List<SupplierItem> findBySupplier(Supplier supplier);

    SupplierItem findFirstByOrderByIdDesc();

    List<SupplierItem> findByItem(Item item);

}
