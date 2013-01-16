package com.cnc.model.resources;

import org.json.simple.JSONObject;

public class LimitedResource extends Resource {
    private long max;

    @Override
    public void update(JSONObject data) {
        super.update(data);
        max = (Long) data.get("m");
    }
}
