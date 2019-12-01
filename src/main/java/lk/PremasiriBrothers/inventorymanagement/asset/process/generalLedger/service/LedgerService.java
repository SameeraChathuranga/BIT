package lk.PremasiriBrothers.inventorymanagement.asset.process.generalLedger.service;


import lk.PremasiriBrothers.inventorymanagement.asset.process.generalLedger.dao.LedgerDao;
import lk.PremasiriBrothers.inventorymanagement.asset.process.generalLedger.entity.Ledger;
import lk.PremasiriBrothers.inventorymanagement.asset.suppliers.entity.Supplier;
import lk.PremasiriBrothers.inventorymanagement.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LedgerService implements AbstractService<Ledger, Integer > { private final LedgerDao ledgerDao;

    @Autowired
    public LedgerService(LedgerDao ledgerDao) {
        this.ledgerDao = ledgerDao;
    }



    public List<Ledger> findBySupplier(Supplier supplier){
        return ledgerDao.findBySupplier(supplier);
    }
    public List<Ledger> findBySupplierS(List<Supplier> suppliers){
        List<Ledger> ledgers = new ArrayList<>();
        for(Supplier supplier : suppliers){
            ledgers.addAll(ledgerDao.findBySupplier(supplier));
        }

        return ledgers;
    }
    @Override
    public List<Ledger> findAll() {
        return null;
    }

    @Override
    public Ledger findById(Integer id) {
        return null;
    }

    @Override
    public Ledger persist(Ledger ledger) {
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public List<Ledger> search(Ledger ledger) {
        return null;
    }

    public Ledger getLastItemId() {
        return ledgerDao.findFirstByOrderByIdDesc();
    }
}
