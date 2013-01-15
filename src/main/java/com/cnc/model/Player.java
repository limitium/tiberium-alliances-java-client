package com.cnc.model;

import org.json.simple.JSONObject;

public class Player {
    private Long id;
    private String name;
    private CombatPoint combatPoint;
    private SupplyPoint supplyPoint;
    private ResearchPoint researchPoint;
    private Credits credits;
    private Alliance alliance;
    private int resourcePackages;

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

        resourcePackages = (int) data.get("l");

        combatPoint.update((JSONObject) data.get("cp"));
        supplyPoint.update((JSONObject) data.get("spp"));
    }
}
