package lk.PremasiriBrothers.inventorymanagement.asset.process.generalLedger.dao;


import lk.PremasiriBrothers.inventorymanagement.asset.item.entity.Item;
import lk.PremasiriBrothers.inventorymanagement.asset.process.generalLedger.entity.Ledger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LedgerDao extends JpaRepository<Ledger, Integer > {

    Ledger findFirstByOrderByIdDesc();

    Ledger findByItem(Item item);

    List<Ledger> findByCreatedAtBetween(LocalDate from, LocalDate to);


}
