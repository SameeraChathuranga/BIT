package lk.PremasiriBrothers.inventorymanagement.asset.suppliers.service;


import lk.PremasiriBrothers.inventorymanagement.asset.suppliers.dao.SupplierDao;
import lk.PremasiriBrothers.inventorymanagement.asset.suppliers.entity.Supplier;
import lk.PremasiriBrothers.inventorymanagement.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SupplierService implements AbstractService<Supplier, Integer > {
    private final SupplierDao supplierDao;

    @Autowired
    public SupplierService(SupplierDao supplierDao) {
        this.supplierDao = supplierDao;
    }


    public List<Supplier> findAll() {
        return supplierDao.findAll();
    }


    public Supplier findById(Integer id) {
        return supplierDao.getOne(id);
    }


    public Supplier persist(Supplier supplier) {
        return supplierDao.save(supplier);
    }


    public boolean delete(Integer id) {
        supplierDao.deleteById(id);
        return false;
    }

    public Supplier lastSupplier(){
        return supplierDao.findFirstByOrderByIdDesc();
    }

public List<Supplier> search(Supplier supplier) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Supplier> supplierExample = Example.of(supplier, matcher);
        return supplierDao.findAll(supplierExample);
    }

    public Supplier findByCode(String code) {
        return supplierDao.findByCode(code);
    }

    public Supplier findByName(String name) {
     return supplierDao.findByName(name);
    }
}
