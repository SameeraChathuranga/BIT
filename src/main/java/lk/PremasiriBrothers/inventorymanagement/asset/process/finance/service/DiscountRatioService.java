package lk.PremasiriBrothers.inventorymanagement.asset.process.finance.service;

import lk.PremasiriBrothers.inventorymanagement.asset.process.finance.dao.DiscountRatioDao;
import lk.PremasiriBrothers.inventorymanagement.asset.process.finance.entity.DiscountRatio;
import lk.PremasiriBrothers.inventorymanagement.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscountRatioService implements AbstractService<DiscountRatio, Integer> {
    private final DiscountRatioDao discountRatioDao;

    @Autowired
    public DiscountRatioService(DiscountRatioDao discountRatioDao) {
        this.discountRatioDao = discountRatioDao;
    }

    @Override
    public List<DiscountRatio> findAll() {
        return discountRatioDao.findAll();
    }

    @Override
    public DiscountRatio findById(Integer id) {
        return discountRatioDao.getOne(id);
    }

    @Override
    public DiscountRatio persist(DiscountRatio discountRatio) {
        return discountRatioDao.save(discountRatio);
    }

    @Override
    public boolean delete(Integer id) {
        discountRatioDao.deleteById(id);
        return false;
    }

    @Override
    public List<DiscountRatio> search(DiscountRatio discountRatio) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<DiscountRatio> discountRatioExample = Example.of(discountRatio, matcher);
        return discountRatioDao.findAll(discountRatioExample);
    }
}
