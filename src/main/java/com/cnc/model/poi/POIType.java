package com.cnc.model.poi;


public enum POIType {
    TIBERIUM_CONTROL_NETWORK_HUB(0, "poitb"),
    CRYSTAL_CONTROL_NETWORK_HUB(1, "poicb"),
    REACTOR(2, "poipb"),

    TUNGSTEN_COMPOUD(3, "poiib"),
    URANIUM_COMPUND(4, "poivb"),
    AIRCRAFT_GUIDANCE_NETWROK_TOWER(5, "poiab"),

    RESONATOR_NETWORK_TOWER(6, "poidb");
    private final long id;
    private final String key;

    POIType(int id, String key) {
        this.id = id;
        this.key = key;
    }

    public static POIType get(long id) {
        for (POIType pt : POIType.values()) {
            if (pt.id == id) {
                return pt;
            }
        }
        return null;
    }

    public String getKey() {
        return key;
    }
}