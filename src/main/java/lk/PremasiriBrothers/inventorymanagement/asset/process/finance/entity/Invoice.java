package lk.PremasiriBrothers.inventorymanagement.asset.process.finance.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lk.PremasiriBrothers.inventorymanagement.asset.customer.entity.Customer;
import lk.PremasiriBrothers.inventorymanagement.asset.process.finance.entity.Enum.InvoicePrintOrNot;
import lk.PremasiriBrothers.inventorymanagement.asset.process.finance.entity.Enum.PaymentMethod;
import lk.PremasiriBrothers.inventorymanagement.security.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = {"balance","discountAmount","bankName","cardNumber"}, allowGetters = true)
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "number", nullable = false, unique = true)
    private String number;

    @Column(name = "payment_method", nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;


    @Column(name = "totalPrice", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPrice;


    @Column(name = "amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal TotalAmount;

    @Column(name = "discountAmount",  precision = 10, scale = 2)
    private BigDecimal discountAmount;

    @Column(name = "amountTendered", precision = 10, scale = 2)
    private BigDecimal amountTendered;

    @Column(name = "balance", precision = 10, scale = 2)
    private BigDecimal balance;

//    @Column(name = "bank_name")
    private String bankName;


//    @Column(name = "card_number")
    private String cardNumber;


    @Column(name = "remarks", length = 150)
    private String remarks;

    @Enumerated(EnumType.STRING)
    private InvoicePrintOrNot invoicePrintOrNot;


    @Column(nullable = false, updatable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate invoicedAt;


    @ManyToOne
    private Customer customer;

    /*@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "invoice_item",
            joinColumns = @JoinColumn(name = "invoice_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id"))
    private List<Item> items;*/
/*
    @ManyToOne
    private Branch branch;*/


    @ManyToOne
    private DiscountRatio discountRatio;
    @ManyToOne
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "invoice")
    private List<InvoiceQuantity> invoiceQuantities;


    /*@ManyToOne
    private Doctor doctor;*/

    /*@OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "invoice_id")
    private List<> invoiceHasLabTests = new ArrayList<>();*/



}
