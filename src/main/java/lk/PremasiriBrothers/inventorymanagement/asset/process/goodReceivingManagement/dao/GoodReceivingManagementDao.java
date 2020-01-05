package lk.PremasiriBrothers.inventorymanagement.asset.process.goodReceivingManagement.dao;


import lk.PremasiriBrothers.inventorymanagement.asset.process.goodReceivingManagement.entity.GoodReceivingManagement;
import lk.PremasiriBrothers.inventorymanagement.asset.suppliers.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface GoodReceivingManagementDao extends JpaRepository<GoodReceivingManagement, Integer> {
    GoodReceivingManagement findFirstByOrderByIdDesc();

    List<GoodReceivingManagement> findByCreatedDateBetween(LocalDate from, LocalDate to);

    List<GoodReceivingManagement> findBySupplierAndCreatedDateBetween(Supplier supplier, LocalDate from, LocalDate to);
}
