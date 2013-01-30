package com.cnc.model.poi;

import org.json.simple.JSONObject;


public class POI {
    private long rank;
    private long ns;
    private long ps;
    private long s;
    private long bonus;

    public void update(JSONObject data, long bonus) {
        this.bonus = bonus;
        rank = (Long) data.get("r");
        ns = (Long) data.get("ns");
        ps = (Long) data.get("ps");
        s = (Long) data.get("s");
    }

    public long getRank() {
        return rank;
    }

    public long getNs() {
        return ns;
    }

    public long getPs() {
        return ps;
    }

    public long getS() {
        return s;
    }

    public long getBonus() {
        return bonus;
    }
}
