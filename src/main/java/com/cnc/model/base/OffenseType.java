package com.cnc.model.base;


public enum OffenseType {
    RIFLEMAN(81),
    PITBULL(86),
    GUARDIAN(88),;
    private final long id;

    OffenseType(long id) {
        this.id = id;
    }

    public static OffenseType get(long id) {
        for (OffenseType ot : OffenseType.values()) {
            if (ot.id == id) {
                return ot;
            }
        }
        return null;
    }
}
