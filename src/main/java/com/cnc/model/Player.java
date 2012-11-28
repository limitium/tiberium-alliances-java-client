package com.cnc.model;

public class Player {
    private int id;
    private String name;
    private CombatPoint combatPoint;
    private SupplyPoint supplyPoint;
    private Gold gold;

    public Player(String name, int id) {
        this.name = name;
        this.id = id;
    }
}
