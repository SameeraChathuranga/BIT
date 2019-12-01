package lk.PremasiriBrothers.inventorymanagement.asset.process.finance.entity;


import lk.PremasiriBrothers.inventorymanagement.util.audit.AuditEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode( callSuper = true )
public class Refund extends AuditEntity {

    @OneToOne( fetch = FetchType.LAZY )
    private Invoice invoice;


    @Column( name = "amount", precision = 10, scale = 2 )
    private BigDecimal amount;


    @Column( name = "reason", length = 45 )
    private String reason;

}
