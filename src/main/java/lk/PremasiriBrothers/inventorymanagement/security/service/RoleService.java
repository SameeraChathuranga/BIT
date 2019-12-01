package lk.PremasiriBrothers.inventorymanagement.security.service;


import lk.PremasiriBrothers.inventorymanagement.security.dao.RoleDao;
import lk.PremasiriBrothers.inventorymanagement.security.entity.Role;
import lk.PremasiriBrothers.inventorymanagement.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoleService implements AbstractService<Role, Integer> {
    private final RoleDao roleDao;

    @Autowired
    public RoleService(RoleDao roleDao) {
        this.roleDao = roleDao;
    }



    public List<Role> findAll() {
        return roleDao.findAll();
    }


    public Role findById(Integer id) {
        return roleDao.getOne(id);
    }


    public Role persist(Role role) {
        return roleDao.save(role);
    }


    public boolean delete(Integer id) {
        roleDao.deleteById(id);
        return false;
    }


    public List<Role> search(Role role) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Role> roleExample = Example.of(role, matcher);
        return roleDao.findAll(roleExample);
    }
}
