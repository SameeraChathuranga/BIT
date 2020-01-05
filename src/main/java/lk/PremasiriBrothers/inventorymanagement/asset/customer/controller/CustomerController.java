package lk.PremasiriBrothers.inventorymanagement.asset.customer.controller;


import lk.PremasiriBrothers.inventorymanagement.asset.commonAsset.Enum.Gender;
import lk.PremasiriBrothers.inventorymanagement.asset.commonAsset.Enum.Title;
import lk.PremasiriBrothers.inventorymanagement.asset.customer.entity.Customer;
import lk.PremasiriBrothers.inventorymanagement.asset.customer.service.CustomerService;
import lk.PremasiriBrothers.inventorymanagement.security.service.UserService;
import lk.PremasiriBrothers.inventorymanagement.util.service.DateTimeAgeService;
import lk.PremasiriBrothers.inventorymanagement.util.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerService customerService;
    private final DateTimeAgeService dateTimeAgeService;
    private final UserService userService;
    private final EmailService emailService;


    @Autowired
    public CustomerController(CustomerService customerService, DateTimeAgeService dateTimeAgeService, UserService userService, EmailService emailService) {
        this.customerService = customerService;
        this.dateTimeAgeService = dateTimeAgeService;
        this.userService = userService;
        this.emailService = emailService;
    }

    @RequestMapping
    public String customerPage(Model model) {
        List<Customer> customers = customerService.findAll();
        model.addAttribute("customers", customers);
        return "customer/customer";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String customerView(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("customerDetail", customerService.findById(id));
        model.addAttribute("addStatus", false);
        return "customer/customer-detail";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editCustomerFrom(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("customer", customerService.findById(id));
          model.addAttribute("newCustomer",customerService.findById(id).getNumber());
        model.addAttribute("addStatus", false);
        model.addAttribute("title", Title.values());
        model.addAttribute("gender", Gender.values());
        return "customer/addCustomer";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String customerAddFrom(Model model) {
        try {
            String input = "";
            if (customerService.lastCustomer() == null) {
                input = "PBC00";
            } else {
                input = customerService.lastCustomer().getNumber();
            }
            String customerNumber= input.replaceAll("[^0-9]+", "");
            Integer CustomerNumber = Integer.parseInt(customerNumber);
            int newCustomerNumber = CustomerNumber+1;
            model.addAttribute("addStatus", true);
            model.addAttribute("lastCustomer",input);
            model.addAttribute("newCustomer","PBC"+ newCustomerNumber);
            model.addAttribute("title", Title.values());
            Model gender = model.addAttribute("gender", Gender.values());
            model.addAttribute("customer", new Customer());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return "customer/addCustomer";
    }

    // Above method support to send data to front end - All List, update, edit
    //Bellow method support to do back end function save, delete, update, search

    @RequestMapping(value = {"/add","/update"}, method = RequestMethod.POST)
    public String addCustomer(@Valid @ModelAttribute Customer customer, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Integer userId = userService.findByUserIdByUserName(auth.getName());
        System.out.println(customer);
        if (result.hasErrors()) {
            for (FieldError error : result.getFieldErrors()) {
                System.out.println(error.getField() + ": " + error.getDefaultMessage());
            }
            model.addAttribute("addStatus", true);
            model.addAttribute("title", Title.values());
            model.addAttribute("gender", Gender.values());
            model.addAttribute("customer", customer);
            return "/customer/addCustomer";
        }
        if (customer.getId() != null){
            customer.setUpdatedAt(dateTimeAgeService.getCurrentDate());
            if(customer.getEmail() != null){
            String message = "Welcome to Excellent Health Solution \n " +
                    "Your detail is updated"+
                    "\n\n\n\n\n Please inform us to if there is any changes on your details" +
                    "\n Kindly request keep your data up to date with us. so we can provide better service for you." +
                    "\n \n \n   Thank You" +
                    "\n Excellent Health Solution";
         boolean isFlag = emailService.sendCustomerRegistrationEmail(customer.getEmail(),"Welcome to Excellent Health Solution ", message);
        if (isFlag){
             redirectAttributes.addFlashAttribute("message", "Successfully Update and Email was sent.");
             redirectAttributes.addFlashAttribute("alertStatus",true);
             customerService.persist(customer);
             return "redirect:/customer";
        }else {
            redirectAttributes.addFlashAttribute("message", "Successfully Update but Email was not sent.");
            redirectAttributes.addFlashAttribute("alertStatus",false);
                customerService.persist(customer);
                return "redirect:/customer";
        }
            }

            customerService.persist(customer);
            return "redirect:/customer";
        }
        if (customer.getEmail() != null){
            String message = "Welcome to Excellent Health Solution \n " +
                    "Your registration number is "+customer.getNumber()+
                    "\nYour Details are"+
                    "\n "+customer.getTitle()+" "+customer.getName()+
                    "\n "+customer.getNic()+
                    "\n "+customer.getDateOfBirth()+
                    "\n "+customer.getMobile()+
                    "\n "+customer.getLand()+
                    "\n\n\n\n\n Please inform us to if there is any changes on your details" +
                    "\n Kindly request keep your data up to date with us. so we can provide better service for you." +
                    "\n \n \n   Thank You" +
                    "\n Excellent Health Solution";
            boolean isFlag = emailService.sendCustomerRegistrationEmail(customer.getEmail(),"Welcome to Excellent Health Solution ", message);
            if (isFlag){
                redirectAttributes.addFlashAttribute("message", "Successfully Add and Email was sent.");
                redirectAttributes.addFlashAttribute("alertStatus",true);
                customer.setCreatedAt(dateTimeAgeService.getCurrentDate());
                customerService.persist(customer);
                return "redirect:/customer";
            }else {
                redirectAttributes.addFlashAttribute("message", "Successfully Add but Email was not sent.");
                redirectAttributes.addFlashAttribute("alertStatus",false);
                customer.setCreatedAt(dateTimeAgeService.getCurrentDate());
                customerService.persist(customer);
                return "redirect:/customer";
            }
        }
        customer.setCreatedAt(dateTimeAgeService.getCurrentDate());
        customerService.persist(customer);
        return "redirect:/customer";
    }


    @RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
    public String removeCustomer(@PathVariable Integer id) {
        customerService.delete(id);
        return "redirect:/customer";
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String search(Model model, Customer customer) {
        model.addAttribute("customerDetail", customerService.search(customer));
        return "customer/customer-detail";
    }

}
