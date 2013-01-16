package com.cnc.model.base;

import org.json.simple.JSONObject;

public class OffenseUnit extends Unit {
    private OffenseType type;

    @Override
    public void update(JSONObject data) {
        super.update(data);
        type = OffenseType.get((Long) data.get("ui"));
    }
}
