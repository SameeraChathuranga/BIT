package lk.PremasiriBrothers.inventorymanagement.asset.process.goodReceivingManagement.entity;

import lk.PremasiriBrothers.inventorymanagement.asset.item.entity.Item;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class GrnQuantity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int requestedQty;

    private int receivedQuantity;

    private BigDecimal amount;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate expireDate;

    @ManyToOne
    private Item item;

    @ManyToOne
    private GoodReceivingManagement goodReceivingManagement;
}
