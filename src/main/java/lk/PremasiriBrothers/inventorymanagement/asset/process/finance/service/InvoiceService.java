package lk.PremasiriBrothers.inventorymanagement.asset.process.finance.service;

import lk.PremasiriBrothers.inventorymanagement.asset.process.finance.dao.InvoiceDao;
import lk.PremasiriBrothers.inventorymanagement.asset.process.finance.entity.Invoice;
import lk.PremasiriBrothers.inventorymanagement.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceService implements AbstractService<Invoice, Integer> {
    private final InvoiceDao invoiceDao;

    @Autowired
    public InvoiceService(InvoiceDao invoiceDao) {
        this.invoiceDao = invoiceDao;
    }

    @Override
    public List<Invoice> findAll() {
        return invoiceDao.findAll();
    }

    @Override
    public Invoice findById(Integer id) {
        return invoiceDao.getOne(id);
    }

    @Override
    public Invoice persist(Invoice invoice) {
        return invoiceDao.save(invoice);
    }

    @Override
    public boolean delete(Integer id) {
        invoiceDao.deleteById(id);
        return false;
    }

    @Override
    public List<Invoice> search(Invoice invoice) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Invoice> invoiceExample = Example.of(invoice, matcher);
        return invoiceDao.findAll(invoiceExample);
    }
}
