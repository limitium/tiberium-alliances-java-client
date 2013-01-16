package com.cnc.model.base;


import org.json.simple.JSONObject;

public class Unit {
    private long id;
    private long x;
    private long y;
    private long level;
    private long h;
    private long ui;

    public void update(JSONObject data) {
        id = (Long) data.get("i");
        x = (Long) data.get("cx");
        y = (Long) data.get("cy");
        level = (Long) data.get("cl");
        h = (Long) data.get("h");
        ui = (Long) data.get("ui");
    }

    public long getId() {
        return id;
    }
}
