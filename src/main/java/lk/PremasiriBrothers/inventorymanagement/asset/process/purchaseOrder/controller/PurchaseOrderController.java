package lk.PremasiriBrothers.inventorymanagement.asset.process.purchaseOrder.controller;

import lk.PremasiriBrothers.inventorymanagement.asset.commonAsset.entity.SupplierItem;
import lk.PremasiriBrothers.inventorymanagement.asset.commonAsset.service.SupplierItemService;
import lk.PremasiriBrothers.inventorymanagement.asset.item.entity.Item;
import lk.PremasiriBrothers.inventorymanagement.asset.item.service.ItemService;
import lk.PremasiriBrothers.inventorymanagement.asset.process.generalLedger.entity.Ledger;
import lk.PremasiriBrothers.inventorymanagement.asset.process.generalLedger.service.LedgerService;
import lk.PremasiriBrothers.inventorymanagement.asset.process.purchaseOrder.entity.Enum.PurchaseOrderStatus;
import lk.PremasiriBrothers.inventorymanagement.asset.process.purchaseOrder.entity.PurchaseOrder;
import lk.PremasiriBrothers.inventorymanagement.asset.process.purchaseOrder.service.PurchaseOrderService;
import lk.PremasiriBrothers.inventorymanagement.asset.suppliers.entity.Supplier;
import lk.PremasiriBrothers.inventorymanagement.asset.suppliers.service.SupplierService;
import lk.PremasiriBrothers.inventorymanagement.security.service.UserService;
import lk.PremasiriBrothers.inventorymanagement.util.service.DateTimeAgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/purchaseOrder")
public class PurchaseOrderController {
    private final PurchaseOrderService purchaseOrderService;
    private final DateTimeAgeService dateTimeAgeService;
    private final UserService userService;
    private final SupplierService supplierService;
    private final SupplierItemService supplierItemService;
    private final ItemService itemService;
    private final LedgerService ledgerService;

    @Autowired
    public PurchaseOrderController(PurchaseOrderService purchaseOrderService, DateTimeAgeService dateTimeAgeService, UserService userService, SupplierService supplierService, SupplierItemService supplierItemService, ItemService itemService, LedgerService ledgerService) {
        this.purchaseOrderService = purchaseOrderService;
        this.dateTimeAgeService = dateTimeAgeService;
        this.userService = userService;
        this.supplierService = supplierService;
        this.supplierItemService = supplierItemService;
        this.itemService = itemService;
        this.ledgerService = ledgerService;
    }

    //give all PO to frontend
    @RequestMapping
    public String purchaseOrderPage(Model model) {
        List<PurchaseOrder> purchaseOrders = purchaseOrderService.findAll();
        model.addAttribute("purchaseOrders", purchaseOrders);
        return "purchaseOrder/purchaseOrder";
    }

    //give PO details
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String purchaseOrderView(@PathVariable("id") Integer id, Model model) {
        PurchaseOrder purchaseOrder = purchaseOrderService.findById(id);
        model.addAttribute("purchaseOrderDetail", purchaseOrder);
        model.addAttribute("addStatus", false);
        return "purchaseOrder/purchaseOrder-detail";
    }

    //OP add  given start
    @GetMapping("/addFind")
    public String pOAddForm(Model model) {
        model.addAttribute("searchArea", true);
        model.addAttribute("addStatus", true);
        return "purchaseOrder/addPurchaseOrder";
    }

    private Supplier commonSearch(Supplier supplier) {
        Supplier supplier1 = supplier;
        if (supplier.getCode() != null) {
            supplier1 = supplierService.findByCode(supplier.getCode());
        }
        if (supplier.getName() != null) {
            supplier1 = supplierService.findByName(supplier.getName());
        }
        return supplier1;
    }

    @PostMapping("/findByItem")
    public String searchByItem(@ModelAttribute("item") Item item, Model model) {
        Item item1 = itemService.findByCode(item.getCode());
        List<SupplierItem> supplierItems = supplierItemService.findSupplier(item1);
        List<Supplier> supplier1 = new ArrayList<>();
        for(SupplierItem s : supplierItems){
            supplier1.add(s.getSupplier());
        }
        List<Ledger> ledgers = ledgerService.findBySupplierS(supplier1);


        model.addAttribute("addStatus", true);
        model.addAttribute("ledgers",ledgers );
        model.addAttribute("searchArea", false);
        model.addAttribute("purchaseOrder", new PurchaseOrder());
        return "purchaseOrder/addPurchaseOrder";
    }

    @PostMapping("/findBySupplier")
    public String searchBySupplier(@ModelAttribute("supplier") Supplier supplier, Model model) {
        List<Item> items = new ArrayList<>();
        Supplier supplier1 = commonSearch(supplier);
        List<SupplierItem> supplierItems = supplierItemService.findItems(supplier1);
        for (SupplierItem s : supplierItems) {
            items.add(s.getItem());
        }
        List<Ledger> ledgers = null;
        if (ledgerService.findBySupplier(supplier1) != null) {
            ledgers = ledgerService.findBySupplier(supplier1);
        }

        model.addAttribute("addStatus", true);
        model.addAttribute("ledgers",ledgers );
        model.addAttribute("items", items);
        model.addAttribute("purchaseOrderStatus", PurchaseOrderStatus.values());
        model.addAttribute("searchArea", false);
        model.addAttribute("purchaseOrder", new PurchaseOrder());
        return "purchaseOrder/addPurchaseOrder";
    }


}
