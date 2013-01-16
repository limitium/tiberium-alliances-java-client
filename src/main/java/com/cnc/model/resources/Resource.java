package com.cnc.model.resources;

import org.json.simple.JSONObject;


public class Resource {
    private long current;
    private double d;
    private long s;

    public void update(JSONObject data) {
        current = (Long) data.get("b");
        s = (Long) data.get("s");
        d = (Double) data.get("d");
    }
}
