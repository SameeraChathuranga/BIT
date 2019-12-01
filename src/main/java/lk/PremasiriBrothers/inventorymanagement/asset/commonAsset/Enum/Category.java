package lk.PremasiriBrothers.inventorymanagement.asset.commonAsset.Enum;

public enum Category {
    TABLET("Tablet"),
    CAPSULE("Capsule"),
    SYRUP("Syrup"),
    DROPS("Drops"),
    INJECTION("Injection"),
    CREAM("Cream"),
    SPRAY("Spray"),
    SITEM("Surgical Item"),
    LOCALAPP("Local Application"),
    RADIOITEM("Radiology Item"),
    DRESSING("Dressing");

    private final String category;

    Category(String category) {
        this.category = category;
    }
    public String getCategory(){
        return category;
    }
}
