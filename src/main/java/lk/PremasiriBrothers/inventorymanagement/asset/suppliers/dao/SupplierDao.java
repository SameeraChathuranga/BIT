package lk.PremasiriBrothers.inventorymanagement.asset.suppliers.dao;


import lk.PremasiriBrothers.inventorymanagement.asset.suppliers.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierDao extends JpaRepository<Supplier, Integer> {
    Supplier findFirstByOrderByIdDesc();

    Supplier findByCode(String code);

    Supplier findByName(String name);
}
