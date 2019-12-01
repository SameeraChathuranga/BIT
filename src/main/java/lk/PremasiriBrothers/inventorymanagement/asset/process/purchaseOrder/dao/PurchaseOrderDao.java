package lk.PremasiriBrothers.inventorymanagement.asset.process.purchaseOrder.dao;

import lk.PremasiriBrothers.inventorymanagement.asset.process.purchaseOrder.entity.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseOrderDao extends JpaRepository<PurchaseOrder, Integer> {

}
