package lk.PremasiriBrothers.inventorymanagement.asset.suppliers.controller;


import lk.PremasiriBrothers.inventorymanagement.asset.commonAsset.Enum.Gender;
import lk.PremasiriBrothers.inventorymanagement.asset.commonAsset.Enum.Title;
import lk.PremasiriBrothers.inventorymanagement.asset.commonAsset.entity.SupplierItem;
import lk.PremasiriBrothers.inventorymanagement.asset.commonAsset.service.SupplierItemService;
import lk.PremasiriBrothers.inventorymanagement.asset.item.entity.Item;
import lk.PremasiriBrothers.inventorymanagement.asset.item.service.ItemService;
import lk.PremasiriBrothers.inventorymanagement.asset.process.generalLedger.service.LedgerService;
import lk.PremasiriBrothers.inventorymanagement.asset.suppliers.entity.Supplier;
import lk.PremasiriBrothers.inventorymanagement.asset.suppliers.service.SupplierService;
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
@RequestMapping("/supplier")
public class SupplierController {
    private final SupplierService supplierService;
    private final ItemService itemService;
    private final DateTimeAgeService dateTimeAgeService;
    private final UserService userService;
    private final EmailService emailService;
    private final SupplierItemService supplierItemService;
    private final LedgerService ledgerService;


    @Autowired
    public SupplierController(SupplierService supplierService, ItemService itemService, DateTimeAgeService dateTimeAgeService, UserService userService, EmailService emailService, SupplierItemService supplierItemService, LedgerService ledgerService) {
        this.supplierService = supplierService;
        this.itemService = itemService;
        this.dateTimeAgeService = dateTimeAgeService;
        this.userService = userService;
        this.emailService = emailService;
        this.supplierItemService = supplierItemService;
        this.ledgerService = ledgerService;
    }

    @RequestMapping
    public String supplierPage(Model model) {
        List<Supplier> suppliers = supplierService.findAll();
        model.addAttribute("suppliers", suppliers);
        return "supplier/supplier";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String supplierView(@PathVariable("id") Integer id, Model model) {
        Supplier supplier = supplierService.findById(id);

        model.addAttribute("supplierDetail", supplier);
        model.addAttribute("items", supplierItemService.findItems(supplier));
        model.addAttribute("addStatus", false);
        return "supplier/supplier-detail";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editSupplierFrom(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("supplier", supplierService.findById(id));
        model.addAttribute("newSupplier", supplierService.findById(id).getCode());
        model.addAttribute("addStatus", false);
        model.addAttribute("title", Title.values());
        model.addAttribute("gender", Gender.values());
        model.addAttribute("items", itemService.findAll());
        return "supplier/addSupplier";
    }

    private void common(Model model) {
        try {
            String input = "";
            if (supplierService.lastSupplier() == null) {
                input = "PBS0";
            } else {
                input = supplierService.lastSupplier().getCode();
            }
            String supplierCode = input.replaceAll("[^0-9]+", "");
            Integer SupplierCode = Integer.parseInt(supplierCode);
            int newSupplierCode = SupplierCode + 1;

            model.addAttribute("lastSupplier", input);
            model.addAttribute("newSupplier", "PBS" + newSupplierCode);
            model.addAttribute("title", Title.values());
            model.addAttribute("items", itemService.findAll());

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String supplierAddFrom(Model model) {
        common(model);
        model.addAttribute("addStatus", true);
        model.addAttribute("supplier", new Supplier());
        return "supplier/addSupplier";
    }

    // Above method support to send data to front end - All List, update, edit
    //Bellow method support to do back end function save, delete, update, search

    @RequestMapping(value = {"/add", "/update"}, method = RequestMethod.POST)
    public String addSupplier(@Valid @ModelAttribute("supplier") Supplier supplier, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Integer userId = userService.findByUserIdByUserName(auth.getName());
        System.out.println("========>"+supplier.toString());
        if (result.hasErrors()) {
            for (FieldError error : result.getFieldErrors()) {
                System.out.println(error.getField() + ": " + error.getDefaultMessage());
            }
            common(model);
            model.addAttribute("addStatus", true);
            model.addAttribute("supplier", supplier);
            return "/supplier/addSupplier";
        }
        if (supplier.getId() != null) {
            supplier.setUpdatedAt(dateTimeAgeService.getCurrentDate());
            if (supplier.getEmail() != null) {
                String message = "Welcome to Excellent Health Solution \n " +
                        "Your detail is updated" +
                        "\n\n\n\n\n Please inform us to if there is any changes on your details" +
                        "\n Kindly request keep your data up to date with us." +
                        "\n \n \n   Thank You" +
                        "\n Excellent Health Solution";

                redirectAttributes.addFlashAttribute("message", "Successfully Updated.");
                Supplier supplier1 = supplierService.persist(supplier);

              /*  Ledger ledger = new Ledger();*/

                SupplierItem supplierItem = new SupplierItem();
                int lastitem = 0;
                try {
                    if(supplierItemService.lastSupplierItem() != null){
                        lastitem = supplierItemService.lastSupplierItem().getId();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                for (Integer i : supplier.getIds()) {
                    supplierItem.setId(lastitem+1);
                    Item item = itemService.findById(i);
                    supplierItem.setItem(item);
                    supplierItem.setSupplier(supplier1);
                    supplierItemService.persist(supplierItem);
                    /*int ledgerId = 0;
                    if(ledgerService.getLastItemId() != null){
                        ledgerId = ledgerService.getLastItemId().getId();
                    }
                    ledger.setId(ledgerId +1);
                    ledger.setCode(UUID.randomUUID().toString());
                    ledger.setSupplier(supplier);
                    ledger.setItem(item);
                    ledger.setAvailableQuantity(0);
                    ledger.setSalePrice(BigDecimal.ZERO);
                    ledgerService.persist(ledger);*/
                    lastitem++;
                }
                return "redirect:/supplier";

            }

            supplierService.persist(supplier);
            return "redirect:/supplier";
        }
        if (supplier.getEmail() != null) {
            String message = "Welcome to Excellent Health Solution \n " +
                    "Your registration number is " + supplier.getLand() +
                    "\nYour Details are" +
                    "\n " + supplier.getCode() +
                    "\n " + supplier.getName() +
                    "\n " + supplier.getAddress() +
                    "\n " + supplier.getEmail() +
                    "\n " + supplier.getLand() +
                    "\n " + supplier.getContactName() +
                    "\n " + supplier.getContactMobile() +
                    "\n " + supplier.getContactEmail() +
                    "\n\n\n\n\n Please inform us to if there is any changes on your details" +
                    "\n Kindly request keep your data up to date with us. so we can provide better service for you." +
                    "\n \n \n   Thank You" +
                    "\n Excellent Health Solution";
            boolean isFlag = emailService.sendSupplierRegistrationEmail(supplier.getEmail(), "Welcome to Excellent Health Solution ", message);
            if (isFlag) {
                redirectAttributes.addFlashAttribute("message", "Successfully Add and Email was sent.");
                redirectAttributes.addFlashAttribute("alertStatus", true);
                supplier.setCreatedAt(dateTimeAgeService.getCurrentDate());

                Supplier supplier1 = supplierService.persist(supplier);

                SupplierItem supplierItem = new SupplierItem();
                int lastitem = 0;
                try {
                    if(supplierItemService.lastSupplierItem() != null){
                        lastitem = supplierItemService.lastSupplierItem().getId();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                System.out.println("id =================>"+lastitem);
                for (Integer i : supplier.getIds()) {
                    supplierItem.setId(lastitem+1);
                    supplierItem.setItem(itemService.findById(i));
                    supplierItem.setSupplier(supplier1);
                    supplierItemService.persist(supplierItem);
                    lastitem++;
                }
                return "redirect:/supplier";
            } else {
                redirectAttributes.addFlashAttribute("message", "Successfully Add but Email was not sent.");
                redirectAttributes.addFlashAttribute("alertStatus", false);
                supplier.setCreatedAt(dateTimeAgeService.getCurrentDate());
                Supplier supplier1 = supplierService.persist(supplier);

                SupplierItem supplierItem = new SupplierItem();
                int lastitem = 0;
                try {
                    if(supplierItemService.lastSupplierItem() != null){
                        lastitem = supplierItemService.lastSupplierItem().getId();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                System.out.println("id =================>"+lastitem);
                for (Integer i : supplier.getIds()) {
                    supplierItem.setId(lastitem+1);
                    supplierItem.setItem(itemService.findById(i));
                    supplierItem.setSupplier(supplier1);
                    supplierItemService.persist(supplierItem);
                    lastitem++;
                }
                return "redirect:/supplier";
            }
        }

        supplier.setCreatedAt(dateTimeAgeService.getCurrentDate());
        Supplier supplier1 = supplierService.persist(supplier);

        SupplierItem supplierItem = new SupplierItem();
        int lastitem = 0;
        try {
            if(supplierItemService.lastSupplierItem() != null){
                lastitem = supplierItemService.lastSupplierItem().getId();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("id =================>"+lastitem);
        for (Integer i : supplier.getIds()) {
            supplierItem.setId(lastitem+1);
            supplierItem.setItem(itemService.findById(i));
            supplierItem.setSupplier(supplier1);
            supplierItemService.persist(supplierItem);
            lastitem++;
        }
        return "redirect:/supplier";
    }


    @RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
    public String removeSupplier(@PathVariable Integer id) {
        supplierService.delete(id);
        return "redirect:/supplier";
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String search(Model model, Supplier supplier) {
        model.addAttribute("supplierDetail", supplierService.search(supplier));
        return "supplier/supplier-detail";
    }

}
