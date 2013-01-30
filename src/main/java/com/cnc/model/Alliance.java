package com.cnc.model;


import com.cnc.model.poi.POI;
import com.cnc.model.poi.POIType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.HashMap;

public class Alliance {
    private Long id;
    private String name;
    private final HashMap<POIType, POI> pois;

    public Alliance() {
        pois = new HashMap<POIType, POI>();
        pois.put(POIType.TIBERIUM_CONTROL_NETWORK_HUB, new POI());
        pois.put(POIType.CRYSTAL_CONTROL_NETWORK_HUB, new POI());
        pois.put(POIType.REACTOR, new POI());
        pois.put(POIType.URANIUM_COMPUND, new POI());
        pois.put(POIType.TUNGSTEN_COMPOUD, new POI());
        pois.put(POIType.AIRCRAFT_GUIDANCE_NETWROK_TOWER, new POI());
        pois.put(POIType.RESONATOR_NETWORK_TOWER, new POI());
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long allianceId) {
        this.id = allianceId;
    }

    public void update(JSONObject data) {
        name = (String) data.get("n");
        id = (Long) data.get("id");

        int poiPosition = 0;
        for (Object o : (JSONArray) data.get("rpois")) {
            POIType poiType = POIType.get(poiPosition);
            if (poiType != null) {
                pois.get(poiType).update((JSONObject) o, (Long) data.get(poiType.getKey()));
            }
            poiPosition++;
        }
    }
}
