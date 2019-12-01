package lk.PremasiriBrothers.inventorymanagement.asset.process.finance.service;

import lk.PremasiriBrothers.inventorymanagement.asset.process.finance.dao.RefundDao;
import lk.PremasiriBrothers.inventorymanagement.asset.process.finance.entity.Refund;
import lk.PremasiriBrothers.inventorymanagement.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RefundService implements AbstractService<Refund, Integer> {
    private final RefundDao refundDao;

    @Autowired
    public RefundService(RefundDao refundDao) {
        this.refundDao = refundDao;
    }

    @Override
    public List<Refund> findAll() {
        return refundDao.findAll();
    }

    @Override
    public Refund findById(Integer id) {
        return refundDao.getOne(id);
    }

    @Override
    public Refund persist(Refund refund) {
        return refundDao.save(refund);
    }

    @Override
    public boolean delete(Integer id) {
        refundDao.deleteById(id);
        return false;
    }

    @Override
    public List<Refund> search(Refund refund) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Refund> refundExample = Example.of(refund, matcher);
        return refundDao.findAll(refundExample);
    }
}
