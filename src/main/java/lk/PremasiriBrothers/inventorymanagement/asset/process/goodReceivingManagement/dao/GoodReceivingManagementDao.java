package lk.PremasiriBrothers.inventorymanagement.asset.process.goodReceivingManagement.dao;


import lk.PremasiriBrothers.inventorymanagement.asset.process.goodReceivingManagement.entity.GoodReceivingManagement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodReceivingManagementDao extends JpaRepository<GoodReceivingManagement, Long> {
}
