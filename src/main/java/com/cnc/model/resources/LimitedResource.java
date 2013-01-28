package com.cnc.model.resources;

import org.json.simple.JSONObject;

public class LimitedResource extends Resource {
    private long max;

    @Override
    public void update(JSONObject data) {
        super.update(data);
        max = (Long) data.get("m");
    }

    public long getMax() {
        return max;
    }

    @Override
    public int getValue() {
        int currentValue = super.getValue();
        return currentValue > max ? (int) Math.max(base, max) : currentValue;
    }
}
