package com.cnc.model.base;


public enum ResourceFieldType {
    CRYSTAL(1),
    TIBERIUM(2),;
    private final long id;

    ResourceFieldType(long id) {
        this.id = id;
    }

    public static ResourceFieldType get(long id) {
        for (ResourceFieldType ot : ResourceFieldType.values()) {
            if (ot.id == id) {
                return ot;
            }
        }
        return null;
    }
}
