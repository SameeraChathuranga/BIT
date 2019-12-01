package lk.PremasiriBrothers.inventorymanagement.asset.commonAsset.Enum;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Title {
    MR("Mr."),
    MRS("Mrs."),
    MISS("Miss."),
    MS("Ms."),
    BABY("Baby."),
    REV("Rev."),
    DR("Dr."),
    DRMRS("Dr(Mrs)."),
    MAST("Master."),
   BabyOf("Baby Of."),
    PASTER("Paster."),
    PRO("Prof."),
    SISTER("Sister."),
    ANIMAL("Animal."),
    NO("OPR");

    private final String title;


    public String getTitle() {
        return this.title;
    }
}
