package lk.PremasiriBrothers.inventorymanagement.asset.customer.service;


import lk.PremasiriBrothers.inventorymanagement.asset.customer.dao.CustomerDao;
import lk.PremasiriBrothers.inventorymanagement.asset.customer.entity.Customer;
import lk.PremasiriBrothers.inventorymanagement.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CustomerService implements AbstractService<Customer, Integer> {
    private final CustomerDao customerDao;

    @Autowired
    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }


    public List<Customer> findAll() {
        return customerDao.findAll();
    }


    public Customer findById(Integer id) {
        return customerDao.getOne(id);
    }


    public Customer persist(Customer customer) {
        return customerDao.save(customer);
    }


    public boolean delete(Integer id) {
        customerDao.deleteById(id);
        return false;
    }


    public List<Customer> search(Customer customer) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Customer> customerExample = Example.of(customer, matcher);
        return customerDao.findAll(customerExample);
    }


    public Customer lastCustomer(){
        return customerDao.findFirstByOrderByIdDesc();
    }


    public Customer findByNIC(String nic) {
        return customerDao.findByNic(nic);
    }

    public Customer findByNumber(String number) {
        return customerDao.findByNumber(number);
    }
}
