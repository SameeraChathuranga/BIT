package lk.PremasiriBrothers.inventorymanagement.asset.item.controller;


import lk.PremasiriBrothers.inventorymanagement.asset.commonAsset.Enum.Category;
import lk.PremasiriBrothers.inventorymanagement.asset.commonAsset.Enum.Status;
import lk.PremasiriBrothers.inventorymanagement.asset.commonAsset.service.SupplierItemService;
import lk.PremasiriBrothers.inventorymanagement.asset.item.entity.Item;
import lk.PremasiriBrothers.inventorymanagement.asset.item.service.ItemService;
import lk.PremasiriBrothers.inventorymanagement.asset.process.generalLedger.entity.Ledger;
import lk.PremasiriBrothers.inventorymanagement.asset.process.generalLedger.service.LedgerService;
import lk.PremasiriBrothers.inventorymanagement.asset.suppliers.service.SupplierService;
import lk.PremasiriBrothers.inventorymanagement.security.service.UserService;
import lk.PremasiriBrothers.inventorymanagement.util.service.DateTimeAgeService;
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
@RequestMapping("/item")
public class ItemController {
    private final ItemService itemService;
    private final DateTimeAgeService dateTimeAgeService;
    private final UserService userService;
    private final SupplierService supplierService;
    private final SupplierItemService supplierItemService;
    private final LedgerService ledgerService;


    @Autowired
    public ItemController(ItemService itemService, DateTimeAgeService dateTimeAgeService, UserService userService, SupplierService supplierService, SupplierItemService supplierItemService, LedgerService ledgerService) {
        this.itemService = itemService;
        this.dateTimeAgeService = dateTimeAgeService;
        this.userService = userService;
        this.supplierService = supplierService;
        this.supplierItemService = supplierItemService;
        this.ledgerService = ledgerService;
    }

    @RequestMapping
    public String itemPage(Model model) {
        List<Item> items = itemService.findAll();
        model.addAttribute("items", items);
        return "item/item";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String itemView(@PathVariable("id") Integer id, Model model) {
        Item item = itemService.findById(id);
        model.addAttribute("suppliers", supplierItemService.findSupplier(item));
        model.addAttribute("itemDetail", item);

        model.addAttribute("addStatus", false);
        return "item/item-detail";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editItemFrom(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("item", itemService.findById(id));
        model.addAttribute("newItem", itemService.findById(id).getCode());
        model.addAttribute("addStatus", false);
        model.addAttribute("category", Category.values());
        model.addAttribute("status", Status.values());
        model.addAttribute("suppliers", supplierService.findAll());
        return "item/addItem";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String itemAddFrom(Model model) {
        try {
            String input = "";
            if (itemService.lastItem() == null) {
                input = "PBI00";
            } else {
                input = itemService.lastItem().getCode();
            }
            String itemNumber = input.replaceAll("[^0-9]+", "");
            Integer ItemNumber = Integer.parseInt(itemNumber);
            int newItemNumber = ItemNumber + 1;
            model.addAttribute("addStatus", true);
            model.addAttribute("lastItem", input);
            model.addAttribute("newItem", "PBI" + newItemNumber);
            model.addAttribute("category", Category.values());
            model.addAttribute("status", Status.values());
            model.addAttribute("supplier", supplierService.findAll());
            model.addAttribute("item", new Item());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return "item/addItem";
    }

    // Above method support to send data to front end - All List, update, edit
    //Bellow method support to do back end function save, delete, update, search

    @RequestMapping(value = {"/add", "/update"}, method = RequestMethod.POST)
    public String addItem(@Valid @ModelAttribute Item item, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Integer userId = userService.findByUserIdByUserName(auth.getName());
        System.out.println(item);
        if (result.hasErrors()) {
            for (FieldError error : result.getFieldErrors()) {
                System.out.println(error.getField() + ": " + error.getDefaultMessage());
            }
            model.addAttribute("addStatus", true);
            model.addAttribute("category", Category.values());
            model.addAttribute("status", Status.values());
            model.addAttribute("suppliers", supplierService.findAll());
            model.addAttribute("item", item);
            return "/item/addItem";
        }
        Ledger ledger = new Ledger();
        if (item.getId() != null) {
            item.setUpdatedAt(dateTimeAgeService.getCurrentDate());
            item.setSoh(item.getSoh());
            item.setCreatedAt(item.getCreatedAt());
            Item item1 = itemService.persist(item);
            ledger = ledgerService.findByItem(item);
            ledger.setCode(item1.getCode());
            ledger.setSalePrice(item.getSelling());
            ledger.setAvailableQuantity(item1.getSoh());
            ledger.setCost(item1.getCost());
            ledger.setReorderLimit(item1.getReorderLimit());
            ledger.setUpdatedAt(dateTimeAgeService.getCurrentDate());
            return "redirect:/item";
        }
        else {
            item.setSoh(0);
            item.setCreatedAt(dateTimeAgeService.getCurrentDate());
            item.setUpdatedAt(dateTimeAgeService.getCurrentDate());
            Item item1 = itemService.persist(item);


            ledger.setItem(item1);
            ledger.setCode(item1.getCode());
            ledger.setSalePrice(item.getSelling());
            ledger.setAvailableQuantity(item1.getSoh());
            ledger.setCost(item1.getCost());
            ledger.setReorderLimit(item1.getReorderLimit());
            ledger.setCreatedAt(dateTimeAgeService.getCurrentDate());
            ledger.setUpdatedAt(dateTimeAgeService.getCurrentDate());
            ledgerService.persist(ledger);
            return "redirect:/item";
        }
    }


    @RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
    public String removeItem(@PathVariable Integer id) {
        itemService.delete(id);
        return "redirect:/item";
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String search(Model model, Item item) {
        model.addAttribute("itemDetail", itemService.search(item));
        return "item/item-detail";
    }

}
