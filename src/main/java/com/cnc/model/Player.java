package com.cnc.model;

import org.json.simple.JSONObject;

public class Player {
    private Long id;
    private String name;
    private CombatPoint combatPoint;
    private SupplyPoint supplyPoint;
    private Gold gold;
    private Alliance alliance;

    public Player() {
        alliance = new Alliance();
    }

    public void update(JSONObject data) {
        id = (Long) data.get("Id");
        name = (String) data.get("Name");
        alliance.setId((Long) data.get("AllianceId"));
        alliance.setName((String) data.get("AllianceName"));
    }
}
