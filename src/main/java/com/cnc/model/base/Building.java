package com.cnc.model.base;

import org.json.simple.JSONObject;

public class Building {
    private long hp;
    private long id;
    private BuildingType type;
    private long x;
    private long y;
    private long level;
    private double rv;
    private double rd;
    private long rs;

    public void update(JSONObject data) {
        hp = (Long) data.get("hp");
        id = (Long) data.get("i");
        x = (Long) data.get("x");
        y = (Long) data.get("y");
        level = (Long) data.get("l");
        type = BuildingType.get((Long) data.get("t"));

        rs = (Long) data.get("rs");
        rd = ((Number) data.get("rd")).doubleValue();
        rv = ((Number) data.get("rv")).doubleValue();
    }

    public long getId() {
        return id;
    }

    public long getHp() {
        return hp;
    }

    public BuildingType getType() {
        return type;
    }

    public long getX() {
        return x;
    }

    public long getY() {
        return y;
    }

    public long getLevel() {
        return level;
    }
}
