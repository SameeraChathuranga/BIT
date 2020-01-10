package lk.PremasiriBrothers.inventorymanagement.asset.process.goodReceivingManagement.service;

import lk.PremasiriBrothers.inventorymanagement.asset.process.goodReceivingManagement.dao.GoodReceivingManagementDao;
import lk.PremasiriBrothers.inventorymanagement.asset.process.goodReceivingManagement.entity.GoodReceivingManagement;
import lk.PremasiriBrothers.inventorymanagement.asset.process.purchaseOrder.entity.PurchaseOrder;
import lk.PremasiriBrothers.inventorymanagement.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class GoodReceivingManagementService implements AbstractService<GoodReceivingManagement, Integer> {
    private final GoodReceivingManagementDao goodReceivingManagementDao;

    @Autowired
    public GoodReceivingManagementService(GoodReceivingManagementDao goodReceivingManagementDao) {
        this.goodReceivingManagementDao = goodReceivingManagementDao;
    }

    @Override
    public List<GoodReceivingManagement> findAll() {
        return goodReceivingManagementDao.findAll();
    }

    @Override
    public GoodReceivingManagement findById(Integer id) {
        return goodReceivingManagementDao.getOne(id);
    }

    @Override
    public GoodReceivingManagement persist(GoodReceivingManagement goodReceivingManagement) {
        return goodReceivingManagementDao.save(goodReceivingManagement);
    }

    @Override
    public boolean delete(Integer id) {
        goodReceivingManagementDao.deleteById(id);
        return false;
    }

    @Override
    public List<GoodReceivingManagement> search(GoodReceivingManagement goodReceivingManagement) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<GoodReceivingManagement> goodReceivingManagementExample = Example.of(goodReceivingManagement, matcher);
        return goodReceivingManagementDao.findAll(goodReceivingManagementExample);
    }

    public GoodReceivingManagement lastGrn(){
        return goodReceivingManagementDao.findFirstByOrderByIdDesc();
    }

    public List<GoodReceivingManagement> findByCreatedAtBetween(LocalDate from, LocalDate to){
        return goodReceivingManagementDao.findByCreatedDateBetween(from, to);
    }
}
