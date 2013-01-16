package com.cnc.model.base;


public enum DefenseType {
    MG_NEST(102),
    WALL(106),;
    private final long id;

    DefenseType(long id) {
        this.id = id;
    }

    public static DefenseType get(long id) {
        for (DefenseType dt : DefenseType.values()) {
            if (dt.id == id) {
                return dt;
            }
        }
        return null;
    }
}
