package lk.PremasiriBrothers.inventorymanagement.asset.process.goodReceivingManagement.controller;

import lk.PremasiriBrothers.inventorymanagement.asset.item.entity.Item;
import lk.PremasiriBrothers.inventorymanagement.asset.item.service.ItemService;
import lk.PremasiriBrothers.inventorymanagement.asset.process.generalLedger.entity.Ledger;
import lk.PremasiriBrothers.inventorymanagement.asset.process.generalLedger.service.LedgerService;
import lk.PremasiriBrothers.inventorymanagement.asset.process.goodReceivingManagement.entity.GoodReceivingManagement;
import lk.PremasiriBrothers.inventorymanagement.asset.process.goodReceivingManagement.entity.GrnQuantity;
import lk.PremasiriBrothers.inventorymanagement.asset.process.goodReceivingManagement.service.GoodReceivingManagementService;
import lk.PremasiriBrothers.inventorymanagement.asset.process.purchaseOrder.entity.Enum.PurchaseOrderStatus;
import lk.PremasiriBrothers.inventorymanagement.asset.process.purchaseOrder.entity.ItemQuantity;
import lk.PremasiriBrothers.inventorymanagement.asset.process.purchaseOrder.entity.PurchaseOrder;
import lk.PremasiriBrothers.inventorymanagement.asset.process.purchaseOrder.service.PurchaseOrderService;
import lk.PremasiriBrothers.inventorymanagement.util.service.DateTimeAgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/grn")
public class GoodReceivingManagementController {
    private final GoodReceivingManagementService goodReceivingManagementService;
    private final PurchaseOrderService purchaseOrderService;
    private final LedgerService ledgerService;
    private final DateTimeAgeService dateTimeAgeService;
    private final ItemService itemService;

    @Autowired
    public GoodReceivingManagementController(GoodReceivingManagementService goodReceivingManagementService, PurchaseOrderService purchaseOrderService, LedgerService ledgerService, DateTimeAgeService dateTimeAgeService, ItemService itemService) {
        this.goodReceivingManagementService = goodReceivingManagementService;
        this.purchaseOrderService = purchaseOrderService;
        this.ledgerService = ledgerService;
        this.dateTimeAgeService = dateTimeAgeService;
        this.itemService = itemService;
    }

    @RequestMapping
    public String grnPage(Model model) {
        List<GoodReceivingManagement> goodReceivingManagements = goodReceivingManagementService.findAll();
        model.addAttribute("grns", goodReceivingManagements);
        return "grn/grn";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String grnView(@PathVariable("id") Integer id, Model model){
        GoodReceivingManagement goodReceivingManagement = goodReceivingManagementService.findById(id);
        model.addAttribute("grnDetail", goodReceivingManagement);
        model.addAttribute("addStatus", false);
        return "grn/grn-detail";
    }

    @GetMapping("/add")
    public String grnAddForm(Model model) {
        List<String> codesList = new ArrayList<>();
        purchaseOrderService.findAll().forEach(purchaseOrder -> codesList.add(purchaseOrder.getCode()));
        model.addAttribute("searchArea", true);
        model.addAttribute("addStatus", true);
        model.addAttribute("pOList", codesList);
        return "grn/addGrn";
    }

    @PostMapping("/findByPOCode")
    public String searchByPOCode(@Valid @ModelAttribute("code") String code, Model model) {
        PurchaseOrder purchaseOrder1 = purchaseOrderService.findByCode(code);
        List<ItemQuantity> itemQuantities = purchaseOrder1.getItemQuantity();

        List<GrnQuantity> grnQuantities = new ArrayList<>();
        for (ItemQuantity itemQuantity : itemQuantities) {
            GrnQuantity grnQuantity = new GrnQuantity();
            grnQuantity.setItem(itemQuantity.getItem());
            grnQuantity.setRequestedQty(itemQuantity.getQuantity());
            grnQuantity.setReceivedQuantity(0);
            grnQuantities.add(grnQuantity);
        }
        GoodReceivingManagement goodReceivingManagement = new GoodReceivingManagement();
        goodReceivingManagement.setGrnQuantities(grnQuantities);
        goodReceivingManagement.setPurchaseOrder(purchaseOrder1);
        goodReceivingManagement.setSupplier(purchaseOrder1.getSupplier());

        model.addAttribute("grn", goodReceivingManagement);
//        model.addAttribute("supplier", );
//        model.addAttribute("purchaseOrder1", purchaseOrder1);
        model.addAttribute("searchArea", false);
        model.addAttribute("addStatus", true);
        return "grn/addGrn";
    }

    @RequestMapping(value = {"/add", "/update"}, method = RequestMethod.POST)
    public String addGRN(@Valid @ModelAttribute("grn") GoodReceivingManagement goodReceivingManagement, BindingResult result, Model model, RedirectAttributes redirectAttributes) {

        List<GrnQuantity> grnQuantities = new ArrayList<>();
        for (GrnQuantity grnQuantity : goodReceivingManagement.getGrnQuantities()) {
            grnQuantity.setGoodReceivingManagement(goodReceivingManagement);
            Ledger ledger = ledgerService.findByItem(grnQuantity.getItem());
            if (ledger != null) {
                if (ledger.getItem().getCost().equals(grnQuantity.getItem().getCost()) ) {
                    ledger.setAvailableQuantity(ledger.getAvailableQuantity() + grnQuantity.getReceivedQuantity());
                    Item item = itemService.findById(grnQuantity.getItem().getId());
                    item.setSoh(ledger.getAvailableQuantity());
                    itemService.persist(item);
                    ledger.setUpdatedAt(dateTimeAgeService.getCurrentDate());
                    grnQuantity.setItem(ledgerService.persist(ledger).getItem());
                    grnQuantities.add(grnQuantity);
                } else {
                    Item item = new Item();
                    item.setDescription(ledger.getItem().getDescription());
                    item.setCategory(ledger.getItem().getCategory());
                    item.setReorderLimit(ledger.getItem().getReorderLimit());
                    item.setStatus(ledger.getItem().getStatus());

                    item.setSoh(grnQuantity.getReceivedQuantity());
                    String c = "";
                    if (itemService.lastItem() == null) {
                        c = "PB00";
                    } else {
                        c = itemService.lastItem().getCode();
                    }
                    String itemNumber = c.replaceAll("[^0-9]+", "");
                    Integer ItemNumber = Integer.parseInt(itemNumber);
                    int newItemNumber = ItemNumber + 1;
                    item.setCode("PB" + newItemNumber);
                    item.setCost(grnQuantity.getItem().getCost());
                    item.setSelling(grnQuantity.getItem().getSelling());
                    item.setCreatedAt(dateTimeAgeService.getCurrentDate());
                    item.setUpdatedAt(dateTimeAgeService.getCurrentDate());
                    Item item1 = itemService.persist(item);

                    Ledger ledger1 = new Ledger();
                    ledger1.setItem(item1);
                    ledger1.setCode(item1.getCode());
                    ledger1.setSalePrice(item1.getSelling());
                    ledger1.setAvailableQuantity(item1.getSoh());
                    ledger1.setCost(item1.getCost());
                    ledger1.setReorderLimit(item1.getReorderLimit());
                    ledger1.setCreatedAt(dateTimeAgeService.getCurrentDate());
                    ledger1.setUpdatedAt(dateTimeAgeService.getCurrentDate());
                    grnQuantity.setItem(ledgerService.persist(ledger1).getItem());
                    grnQuantities.add(grnQuantity);
                }
            }
        }
        String grn = "";
        if (goodReceivingManagementService.lastGrn() == null) {
            grn = "PBGRN00";
        } else {
            grn = goodReceivingManagementService.lastGrn().getCode();
        }
        String grnNumber = grn.replaceAll("[^0-9]+", "");
        Integer grnN = Integer.parseInt(grnNumber);
        int newGrnNumber = grnN + 1;
        goodReceivingManagement.setCode("PBGRN" + newGrnNumber);
        goodReceivingManagement.setCreatedDate(dateTimeAgeService.getCurrentDate());
        goodReceivingManagement.setUpdatedDate(dateTimeAgeService.getCurrentDate());
        goodReceivingManagement.setGrnQuantities(grnQuantities);
        goodReceivingManagementService.persist(goodReceivingManagement);

        PurchaseOrder purchaseOrder = purchaseOrderService.findById(goodReceivingManagement.getPurchaseOrder().getId());
        if (purchaseOrder.getItemQuantity().size() != goodReceivingManagement.getGrnQuantities().size()) {
            purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.PARTIALY);
            purchaseOrderService.persist(purchaseOrder);
        } else {
            for (GrnQuantity quantity : goodReceivingManagement.getGrnQuantities()) {
                if (!purchaseOrder.getItemQuantity().isEmpty()) {
                    for (ItemQuantity purchase : purchaseOrder.getItemQuantity()) {
                        if (quantity.getItem().getCode().equals(purchase.getItem().getCode())) {
                            if (quantity.getReceivedQuantity() >= purchase.getQuantity()) {
                                purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.FULLY);
                            } else {
                                purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.PARTIALY);
                            }
                        }
                    }
                }
            }
            purchaseOrderService.persist(purchaseOrder);
        }


        return "redirect:/grn";
    }
}
