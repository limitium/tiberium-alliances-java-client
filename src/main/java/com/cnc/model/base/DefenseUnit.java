package com.cnc.model.base;


import org.json.simple.JSONObject;

public class DefenseUnit extends Unit {
    private DefenseType type;

    @Override
    public void update(JSONObject data) {
        super.update(data);
        type = DefenseType.get((Long) data.get("ui"));
    }
}
