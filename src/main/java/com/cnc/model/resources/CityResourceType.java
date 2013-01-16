package com.cnc.model.resources;


public enum CityResourceType {
    TIBERIUM(1),
    CRYSTAL(2),
    POWER(5),
    CREDITS(10),;
    private final long id;

    CityResourceType(long id) {
        this.id = id;
    }

    public static CityResourceType get(long id) {
        for (CityResourceType ctr : CityResourceType.values()) {
            if (ctr.id == id) {
                return ctr;
            }
        }
        return null;
    }

}
