package lk.PremasiriBrothers.inventorymanagement.asset.process.goodReceivingManagement.entity.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GRNStatus {
    SUP("Sufficient"),
    AVA("Available"),
    WAR("Warning"),
    NILL("Not Available");

    private final String gRNStatus;
}
