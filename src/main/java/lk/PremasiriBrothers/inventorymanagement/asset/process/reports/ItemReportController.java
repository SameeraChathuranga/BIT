package lk.PremasiriBrothers.inventorymanagement.asset.process.reports;

import lk.PremasiriBrothers.inventorymanagement.asset.item.entity.Item;
import lk.PremasiriBrothers.inventorymanagement.asset.item.service.ItemService;
import lk.PremasiriBrothers.inventorymanagement.asset.process.goodReceivingManagement.entity.GoodReceivingManagement;
import lk.PremasiriBrothers.inventorymanagement.asset.process.goodReceivingManagement.service.GoodReceivingManagementService;
import lk.PremasiriBrothers.inventorymanagement.asset.process.purchaseOrder.entity.PurchaseOrder;
import lk.PremasiriBrothers.inventorymanagement.asset.process.purchaseOrder.service.PurchaseOrderService;
import lk.PremasiriBrothers.inventorymanagement.asset.process.reports.entityHelp.ReportHelp;
import lk.PremasiriBrothers.inventorymanagement.security.entity.User;
import lk.PremasiriBrothers.inventorymanagement.security.service.UserService;
import lk.PremasiriBrothers.inventorymanagement.util.service.DateTimeAgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/itemReport")
public class ItemReportController {

    private final PurchaseOrderService purchaseOrderService;
    private final GoodReceivingManagementService goodReceivingManagementService;
    private final UserService userService;
    private final ItemService itemService;
    private final DateTimeAgeService dateTimeAgeService;


    @Autowired
    public ItemReportController(PurchaseOrderService purchaseOrderService, GoodReceivingManagementService goodReceivingManagementService, UserService userService, ItemService itemService, DateTimeAgeService dateTimeAgeService) {

        this.purchaseOrderService = purchaseOrderService;
        this.goodReceivingManagementService = goodReceivingManagementService;

        this.userService = userService;

        this.itemService = itemService;
        this.dateTimeAgeService = dateTimeAgeService;

    }

    @GetMapping("/form")
    public String dailyReport(Model model){
        model.addAttribute("givenDate", dateTimeAgeService.getCurrentDate().toString());
        User user = userService.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("dateObject", new ReportHelp());
        model.addAttribute("date", dateTimeAgeService.getCurrentDateTime());
        model.addAttribute("user", user.getEmployee().getTitle().getTitle() + " " + user.getEmployee().getName());
        List<Item> items = itemService.findByCreatedAtBetween(dateTimeAgeService.getCurrentDate(), dateTimeAgeService.getCurrentDate());
        model.addAttribute("items", items);
        return "summary/ItemSummary";
    }

    @PostMapping("/search")
    public String rangeReport(@Valid @ModelAttribute ReportHelp reportHelp, Model model){
        User user = userService.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());

        model.addAttribute("dateObject", new ReportHelp());
        model.addAttribute("date", dateTimeAgeService.getCurrentDateTime());
        model.addAttribute("user", user.getEmployee().getTitle().getTitle() + " " + user.getEmployee().getName());

        model.addAttribute("givenDate", "FROM : " + reportHelp.getStartDate().toString() + "  TO : " + reportHelp.getEndDate().toString());
        List<Item> items = itemService.findByCreatedAtBetween(reportHelp.getStartDate(),reportHelp.getEndDate());
        model.addAttribute("items", items);
        return "summary/ItemSummary";
    }

    @GetMapping("/purchaseOrderForm")
    public String purchaseOrderDailyReport(Model model){
        model.addAttribute("givenDate", dateTimeAgeService.getCurrentDate().toString());
        User user = userService.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("dateObject", new ReportHelp());
        model.addAttribute("date", dateTimeAgeService.getCurrentDateTime());
        model.addAttribute("user", user.getEmployee().getTitle().getTitle() + " " + user.getEmployee().getName());
        List<PurchaseOrder> purchaseOrders = purchaseOrderService.findByCreatedAtBetween(dateTimeAgeService.getCurrentDate(), dateTimeAgeService.getCurrentDate());
        model.addAttribute("purchaseOrders", purchaseOrders);
        return "summary/POSummary";
    }

    @PostMapping("/POrange")
    public String purchaseOrderRangeReport(@Valid @ModelAttribute ReportHelp reportHelp, Model model){
        User user = userService.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());

        model.addAttribute("dateObject", new ReportHelp());
        model.addAttribute("date", dateTimeAgeService.getCurrentDateTime());
        model.addAttribute("user", user.getEmployee().getTitle().getTitle() + " " + user.getEmployee().getName());

        model.addAttribute("givenDate", "FROM : " + reportHelp.getStartDate().toString() + "  TO : " + reportHelp.getEndDate().toString());
        List<PurchaseOrder> purchaseOrders = purchaseOrderService.findByCreatedAtBetween(reportHelp.getStartDate(), reportHelp.getEndDate());
        model.addAttribute("purchaseOrders", purchaseOrders);
        return "summary/POSummary";
    }

    @GetMapping("/goodReceivedForm")
    public String goodReceviedDailyReport(Model model){
        model.addAttribute("givenDate", dateTimeAgeService.getCurrentDate().toString());
        User user = userService.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("dateObject", new ReportHelp());
        model.addAttribute("date", dateTimeAgeService.getCurrentDateTime());
        model.addAttribute("user", user.getEmployee().getTitle().getTitle() + " " + user.getEmployee().getName());
        List<GoodReceivingManagement> goodReceivingManagements = goodReceivingManagementService.findByCreatedAtBetween(dateTimeAgeService.getCurrentDate(), dateTimeAgeService.getCurrentDate());
        model.addAttribute("goodReceivingManagements", goodReceivingManagements);
        return "summary/GRNSummary";
    }

    @PostMapping("/GRNrange")
    public String goodReceviedDailyReport(@Valid @ModelAttribute ReportHelp reportHelp, Model model){
        User user = userService.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());

        model.addAttribute("dateObject", new ReportHelp());
        model.addAttribute("date", dateTimeAgeService.getCurrentDateTime());
        model.addAttribute("user", user.getEmployee().getTitle().getTitle() + " " + user.getEmployee().getName());

        model.addAttribute("givenDate", "FROM : " + reportHelp.getStartDate().toString() + "  TO : " + reportHelp.getEndDate().toString());
        List<GoodReceivingManagement> goodReceivingManagements = goodReceivingManagementService.findByCreatedAtBetween(reportHelp.getStartDate(), reportHelp.getEndDate());
        model.addAttribute("goodReceivingManagements", goodReceivingManagements);
        return "summary/GRNSummary";
    }
}
