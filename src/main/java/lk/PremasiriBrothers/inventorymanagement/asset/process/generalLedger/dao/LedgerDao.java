package lk.PremasiriBrothers.inventorymanagement.asset.process.generalLedger.dao;


import lk.PremasiriBrothers.inventorymanagement.asset.process.generalLedger.entity.Ledger;
import lk.PremasiriBrothers.inventorymanagement.asset.suppliers.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LedgerDao extends JpaRepository<Ledger, Integer > {
    List<Ledger> findBySupplier(Supplier supplier);
    Ledger findFirstByOrderByIdDesc();
}
