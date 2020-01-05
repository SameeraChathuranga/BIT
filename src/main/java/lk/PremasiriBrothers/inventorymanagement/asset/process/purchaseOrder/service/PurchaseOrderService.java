package lk.PremasiriBrothers.inventorymanagement.asset.process.purchaseOrder.service;

import lk.PremasiriBrothers.inventorymanagement.asset.process.purchaseOrder.dao.PurchaseOrderDao;
import lk.PremasiriBrothers.inventorymanagement.asset.process.purchaseOrder.entity.PurchaseOrder;
import lk.PremasiriBrothers.inventorymanagement.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PurchaseOrderService implements AbstractService<PurchaseOrder, Integer> {
    private final PurchaseOrderDao purchaseOrderDao;

    @Autowired
    public PurchaseOrderService(PurchaseOrderDao purchaseOrderDao) {
        this.purchaseOrderDao = purchaseOrderDao;
    }

    @Override
    public List<PurchaseOrder> findAll() {
        return purchaseOrderDao.findAll();
    }

    @Override
    public PurchaseOrder findById(Integer id) {
        return purchaseOrderDao.getOne(id);
    }

    @Override
    public PurchaseOrder persist(PurchaseOrder purchaseOrder) {
        return purchaseOrderDao.save(purchaseOrder);
    }

    @Override
    public boolean delete(Integer id) {
        purchaseOrderDao.deleteById(id);
        return false;
    }

    @Override
    public List<PurchaseOrder> search(PurchaseOrder purchaseOrder) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<PurchaseOrder> purchaseOrderExample = Example.of(purchaseOrder, matcher);
        return purchaseOrderDao.findAll(purchaseOrderExample);
    }

    public PurchaseOrder findLastPONumber() {
        return purchaseOrderDao.findFirstByOrderByIdDesc();
    }

    public PurchaseOrder findByCode(String code){
        return purchaseOrderDao.findByCode(code);
    }

    public List<PurchaseOrder> findByCreatedAtBetween(LocalDate from, LocalDate to){
        return purchaseOrderDao.findByCreatedDateBetween(from, to);
    }
}
