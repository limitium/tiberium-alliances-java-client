package com.cnc.model;


import org.json.simple.JSONObject;

public class CombatPoint {
    private long current;
    private long max;
    private double d;
    private long s;

    public void update(JSONObject data) {
        current = (Long) data.get("b");
        max = (Long) data.get("m");
        s = (Long) data.get("s");
        d = (Long) data.get("d");
    }

}
