package lk.PremasiriBrothers.inventorymanagement.asset.employee.dao;


import lk.PremasiriBrothers.inventorymanagement.asset.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EmployeeDao extends JpaRepository<Employee, Integer> {
    Employee findFirstByOrderByIdDesc();

}
