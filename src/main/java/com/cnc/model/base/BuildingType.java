package com.cnc.model.base;

public enum BuildingType {
    CONSTRUCTION_YARD(1),
    REFINARY(2),
    SILO(5),
    POWER_PLANT(10),
    ACCUMULATOR(16),
    COMMAND_CENTER(24),
    HARVESTER(32),
    BARRACKS(34),
    FACTORY(35),
    AIRFIELD(36),
    DEFENCE_HQ(40),
    DEFENCE_FACILITY(42),
    STRIKE_SUPPORT(82),;
    private final long id;

    BuildingType(long id) {
        this.id = id;
    }

    public static BuildingType get(long id) {
        for (BuildingType bt : BuildingType.values()) {
            if (bt.id == id) {
                return bt;
            }
        }
        return null;
    }
}
