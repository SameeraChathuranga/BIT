package lk.PremasiriBrothers.inventorymanagement.asset.commonAsset.Enum;

public enum Category {

    BEVERAGE("Beverages"),
    BISCUITS("Biscuits"),
    CANNED("Canned"),
    COOKINGOILS("Cooking Oils"),
    COMMODITIES("Commodoties"),
    DAIRY("Dairy"),
    DRY("Dry"),
    FLOUR ("Flour"),
    DBACKING("Baking Goods"),
    FROZEN("Frozen Foods"),
    MEAT("Meat"),
    PPRODUCE("Produce"),
    CLEANERS("Cleaners"),
    PERSONALCARE("Personal Care"),
    SPICES("Spices"),
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
