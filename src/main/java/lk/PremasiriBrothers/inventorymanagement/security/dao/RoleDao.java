package lk.PremasiriBrothers.inventorymanagement.security.dao;


import lk.PremasiriBrothers.inventorymanagement.security.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface RoleDao extends JpaRepository<Role, Integer> {
}
