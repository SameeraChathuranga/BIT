package lk.PremasiriBrothers.inventorymanagement.asset.commonAsset.Enum;

public enum Category {

    BEVERAGE("Beverages"),
    CANNED("Canned"),
    DAIRY("Dairy"),
    DRY("Dry"),
    DBACKING("Baking Goods"),
    FROZEN("Frozen Foods"),
    MEAT("Meat"),
    PPRODUCE("Produce"),
    CLEANERS("Cleaners"),
    PERSONALCARE("Personal Care"),
    OTHER("Other");





    ;

    private final String category;

    Category(String category) {
        this.category = category;
    }
    public String getCategory(){
        return category;
    }
}
