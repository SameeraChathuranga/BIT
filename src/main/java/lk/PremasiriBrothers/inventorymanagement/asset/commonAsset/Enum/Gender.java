package lk.PremasiriBrothers.inventorymanagement.asset.commonAsset.Enum;

public enum Gender {
    MALE("Male"),
    FEMALE("Female");

    private final String gender;

    Gender(String gender) {
        this.gender = gender;
    }


    public String getGender() {
        return gender;
    }
}
