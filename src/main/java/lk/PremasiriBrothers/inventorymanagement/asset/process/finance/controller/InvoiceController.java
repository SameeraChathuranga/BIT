package lk.PremasiriBrothers.inventorymanagement.asset.process.finance.controller;

import lk.PremasiriBrothers.inventorymanagement.asset.commonAsset.Enum.Gender;
import lk.PremasiriBrothers.inventorymanagement.asset.commonAsset.Enum.Title;
import lk.PremasiriBrothers.inventorymanagement.asset.customer.controller.CustomerRestController;
import lk.PremasiriBrothers.inventorymanagement.asset.customer.entity.Customer;
import lk.PremasiriBrothers.inventorymanagement.asset.customer.service.CustomerService;
import lk.PremasiriBrothers.inventorymanagement.asset.item.entity.Item;
import lk.PremasiriBrothers.inventorymanagement.asset.item.service.ItemService;
import lk.PremasiriBrothers.inventorymanagement.asset.process.finance.entity.Enum.PaymentMethod;
import lk.PremasiriBrothers.inventorymanagement.asset.process.finance.entity.Invoice;
import lk.PremasiriBrothers.inventorymanagement.asset.process.finance.entity.InvoiceQuantity;
import lk.PremasiriBrothers.inventorymanagement.asset.process.finance.service.DiscountRatioService;
import lk.PremasiriBrothers.inventorymanagement.asset.process.finance.service.InvoiceService;
import lk.PremasiriBrothers.inventorymanagement.asset.process.generalLedger.entity.Ledger;
import lk.PremasiriBrothers.inventorymanagement.asset.process.generalLedger.service.LedgerService;
import lk.PremasiriBrothers.inventorymanagement.security.service.UserService;
import lk.PremasiriBrothers.inventorymanagement.util.service.DateTimeAgeService;
import lk.PremasiriBrothers.inventorymanagement.util.service.FileHandelService;
import lk.PremasiriBrothers.inventorymanagement.util.service.OperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/invoice")
public class InvoiceController {
    private final InvoiceService invoiceService;
    private final CustomerService customerService;
    private final ItemService itemService;
    private final DiscountRatioService discountRatioService;
    private final DateTimeAgeService dateTimeAgeService;
    private final LedgerService ledgerService;
    private final UserService userService;
    private final OperatorService operatorService;
    private final FileHandelService fileHandelService;
    private final ServletContext context;

    @Autowired
    public InvoiceController(InvoiceService invoiceService, CustomerService customerService, ItemService itemService, DiscountRatioService discountRatioService, DateTimeAgeService dateTimeAgeService, LedgerService ledgerService, UserService userService, OperatorService operatorService, FileHandelService fileHandelService, ServletContext context) {
        this.invoiceService = invoiceService;
        this.customerService = customerService;
        this.itemService = itemService;
        this.discountRatioService = discountRatioService;
        this.dateTimeAgeService = dateTimeAgeService;
        this.ledgerService = ledgerService;
        this.userService = userService;
        this.operatorService = operatorService;
        this.fileHandelService = fileHandelService;
        this.context = context;
    }

    @RequestMapping
    public String allInvoices(Model model) {
        List<Invoice> invoices = invoiceService.findAll();
        model.addAttribute("invoices", invoices);
        return "invoice/invoice";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String invoiceView(@PathVariable("id") Integer id, Model model) {
        Invoice invoice = invoiceService.findById(id);
        model.addAttribute("invoiceDetail", invoice);
        model.addAttribute("addStatus", false);
        return "invoice/invoice-detail";
    }

    @GetMapping("/addForm")
    public String giveForm(Model model) {
        /*Customer relevant details*/
        try {
            String input = "";
            if (customerService.lastCustomer() == null) {
                input = "PBC00";
            } else {
                input = customerService.lastCustomer().getNumber();
            }
            String customerNumber = input.replaceAll("[^0-9]+", "");
            Integer CustomerNumber = Integer.parseInt(customerNumber);
            int newCustomerNumber = CustomerNumber + 1;
            model.addAttribute("addStatus", true);
            model.addAttribute("lastCustomer", input);
            model.addAttribute("newCustomer", "PBC" + newCustomerNumber);
            model.addAttribute("title", Title.values());
            model.addAttribute("gender", Gender.values());
            model.addAttribute("customer", new Customer());
            model.addAttribute("invoice", new Invoice());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        /*Medicines all*/
        List<Item> items = itemService.findAll().stream().filter(x -> x.getSoh() > 0).collect(Collectors.toList());
        model.addAttribute("items", items);

        /*Discounts and Payment Methods*/
        model.addAttribute("discountRatios", discountRatioService.findAll());
        model.addAttribute("paymentMethods", PaymentMethod.values());
        /*customer search related links*/
        model.addAttribute("customerUrl", MvcUriComponentsBuilder
                .fromMethodName(CustomerRestController.class, "searchCustomer", "")
                .build()
                .toString());
        return "invoice/addInvoice";
    }

    public void commonInvoice(Invoice invoice, Integer userId, HttpServletRequest request, HttpServletResponse response) {
        List<InvoiceQuantity> invoiceqty = invoice.getInvoiceQuantities().stream().
                filter(value -> value.getQuantity() != 0).collect(Collectors.toList());

        invoiceqty.forEach(v -> v.setInvoice(invoice));

        System.out.println(invoiceqty);

        String c = "";
        if (invoiceService.lastInvoice() == null) {
            c = "PBIN00";
        } else {
            c = invoiceService.lastInvoice().getNumber().toString();
        }
        String invoiceNumber = c.replaceAll("[^0-9]+", "");
        int InNumber = Integer.parseInt(invoiceNumber);
        int newInvoiceNumber = InNumber + 1;
        invoice.setNumber("PBIN" + newInvoiceNumber);
        invoice.setInvoiceQuantities(invoiceqty);
        invoice.setUser(userService.findById(userId));
        invoice.setInvoicedAt(dateTimeAgeService.getCurrentDate());
        invoice.setDiscountAmount(operatorService.subtraction(invoice.getTotalPrice(), invoice.getTotalAmount()));
        Invoice invoice1 = invoiceService.persist(invoice);

        boolean isFlag = invoiceService.createPdf(invoice1, context, request, response);
        invoice1.getInvoiceQuantities().forEach(
                x -> {
                    Item item = itemService.findById(x.getItem().getId());
                    int availableItem = item.getSoh();
                    item.setSoh(availableItem - x.getQuantity());
                    itemService.persist(item);
                    Ledger ledger = ledgerService.findByItem(x.getItem());
                    int availableLeger = ledger.getAvailableQuantity();
                    ledger.setAvailableQuantity(availableLeger - x.getQuantity());
                    ledgerService.persist(ledger);
                }
        );
        System.out.println("flag "+ isFlag);
        if (isFlag) {
            String fullPath = request.getServletContext().getRealPath("/resources/report/" + "invoices" + ".pdf");
            fileHandelService.filedownload(fullPath, response, "invoices.pdf");
        }
    }

    @RequestMapping(value = {"/add"}, method = RequestMethod.POST)
    public String addInvoice(@ModelAttribute("invoice") Invoice invoice, HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Integer userId = userService.findByUserIdByUserName(auth.getName());
       if (invoice.getCustomer().getNic().length() == 0) {
           System.out.println(" nic no");
           invoice.setCustomer(customerService.findById(1));
       } else {
            invoice.setCustomer(customerService.persist(invoice.getCustomer()));
       }
        commonInvoice(invoice, userId, request, response);
        return "redirect:/invoice/addForm";
    }

}
