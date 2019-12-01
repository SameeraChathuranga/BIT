package lk.PremasiriBrothers.inventorymanagement.asset.process.purchaseOrder.entity.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PurchaseOrderStatus {
    PARTIALY("Partial Completed"),
    FULLY("Fully Completed"),
    NOT("Not Completed"),
    CANCELLED("Cancelled");

    private final String purchaseOrderStatus;
}
