package lk.PremasiriBrothers.inventorymanagement.asset.employee.controller;


import lk.PremasiriBrothers.inventorymanagement.asset.commonAsset.Enum.Gender;
import lk.PremasiriBrothers.inventorymanagement.asset.commonAsset.Enum.Title;
import lk.PremasiriBrothers.inventorymanagement.asset.employee.entity.Employee;
import lk.PremasiriBrothers.inventorymanagement.asset.employee.entity.Enum.CivilStatus;
import lk.PremasiriBrothers.inventorymanagement.asset.employee.entity.Enum.Designation;
import lk.PremasiriBrothers.inventorymanagement.asset.employee.entity.Enum.EmployeeStatus;
import lk.PremasiriBrothers.inventorymanagement.asset.employee.service.EmployeeService;
import lk.PremasiriBrothers.inventorymanagement.security.entity.Role;
import lk.PremasiriBrothers.inventorymanagement.security.entity.User;
import lk.PremasiriBrothers.inventorymanagement.security.service.RoleService;
import lk.PremasiriBrothers.inventorymanagement.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/select")
public class EmployeeCtrl {
    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Autowired
    private EmployeeService employeeService;

    private final PasswordEncoder passwordEncoder;

    public EmployeeCtrl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/create")
    public User saveEmployee(){
        Employee employee = new Employee();
        employee.setName("Sameera");
        employee.setCallingName("sameera");
        employee.setAddress("62/3/1, Pelpolawatta, Godagama, Meegoda");
        employee.setCivilStatus(CivilStatus.UNMARRIED);
        employee.setDateOfBirth(LocalDate.now());
        employee.setDesignation(Designation.ADMIN);
        employee.setGender(Gender.MALE);
        employee.setEmail("sameerachathuranga76@gmail.com");
        employee.setEmployeeStatus(EmployeeStatus.WORKING);
        employee.setLand("");
        employee.setMobile("0769214150");
        employee.setNumber("E0001");
        employee.setNic("199316600268");
        employee.setTitle(Title.MR);
        employee.setDoassignment(LocalDate.now());

        List<Role> set = new ArrayList<>();
        Role role = new Role();
        role.setRoleName("MANAGER");
        set.add(role);


        User user = new User();
        user.setEmployee(employee);


        user.setRoles(set);
        user.setEnabled(true);
        user.setUsername("sameera");
        user.setPassword("12345");
        userService.persist(user);

        return userService.findById(1);
    }

    @GetMapping(value = "/emp" , produces = "application/json")
    public void sendOne(){
        Employee employee = employeeService.findById(1);
        System.out.println(employee);

    }

}
