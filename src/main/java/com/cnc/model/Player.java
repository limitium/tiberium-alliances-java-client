package com.cnc.model;

import com.cnc.model.resources.CombatPoint;
import com.cnc.model.resources.Credits;
import com.cnc.model.resources.SupplyPoint;
import org.json.simple.JSONObject;

public class Player {
    private long id;
    private String name = "";
    private CombatPoint combatPoint;
    private SupplyPoint supplyPoint;
    private long researchPoint;
    private long rating;
    private Credits credits;
    private Alliance alliance;
    private String fraction;

    public Player() {
        alliance = new Alliance();
        credits = new Credits();
        combatPoint = new CombatPoint();
        supplyPoint = new SupplyPoint();
    }

    public void update(JSONObject data) {
        id = (Long) data.get("i");
        name = (String) data.get("n");
//        alliance.setId((Long) data.get("AllianceId"));
//        alliance.setName((String) data.get("AllianceName"));


        combatPoint.update((JSONObject) data.get("cp"));
        supplyPoint.update((JSONObject) data.get("spp"));
        credits.update((JSONObject) data.get("c"));
        researchPoint = (Long) data.get("rp");

        fraction =  ((Number)data.get("f")).intValue()==2?"n":"g";
        rating = (Long) data.get("r");
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public CombatPoint getCombatPoint() {
        return combatPoint;
    }

    public SupplyPoint getSupplyPoint() {
        return supplyPoint;
    }

    public long getResearchPoint() {
        return researchPoint;
    }

    public Credits getCredits() {
        return credits;
    }

    public Alliance getAlliance() {
        return alliance;
    }

    public long getRating() {
        return rating;
    }

    public String getFraction() {
        return fraction;
    }
}
