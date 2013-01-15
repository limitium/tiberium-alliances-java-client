package com.cnc.model;


import org.json.simple.JSONObject;

public class ResearchPoint {
    private long current;
    private double d;
    private long s;

    public void update(JSONObject data) {
        current = (Long) data.get("b");
        s = (Long) data.get("s");
        d = (Long) data.get("d");
    }

}
