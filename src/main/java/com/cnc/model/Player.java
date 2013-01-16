package com.cnc.model;

import com.cnc.model.resources.CombatPoint;
import com.cnc.model.resources.Credits;
import com.cnc.model.resources.ResearchPoint;
import com.cnc.model.resources.SupplyPoint;
import org.json.simple.JSONObject;

public class Player {
    private long id;
    private String name;
    private CombatPoint combatPoint;
    private SupplyPoint supplyPoint;
    private ResearchPoint researchPoint;
    private Credits credits;
    private Alliance alliance;
    private long resourcePackages;

    public Player() {
        alliance = new Alliance();
        credits = new Credits();
        combatPoint = new CombatPoint();
        supplyPoint = new SupplyPoint();
    }

    public void update(JSONObject data) {
        id = (Long) data.get("Id");
        name = (String) data.get("Name");
        alliance.setId((Long) data.get("AllianceId"));
        alliance.setName((String) data.get("AllianceName"));

        resourcePackages = (Long) data.get("l");

        combatPoint.update((JSONObject) data.get("cp"));
        supplyPoint.update((JSONObject) data.get("spp"));
        credits.update((JSONObject) data.get("g"));
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

    public ResearchPoint getResearchPoint() {
        return researchPoint;
    }

    public Credits getCredits() {
        return credits;
    }

    public Alliance getAlliance() {
        return alliance;
    }

    public long getResourcePackages() {
        return resourcePackages;
    }
}
