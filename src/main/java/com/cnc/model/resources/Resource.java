package com.cnc.model.resources;

import com.cnc.game.Client;
import org.json.simple.JSONObject;


public class Resource {
    private Double current;
    private double d;
    private long s;

    public void update(JSONObject data) {
        current = ((Number) data.get("b")).doubleValue();
        s = (Long) data.get("s");
        d = ((Number) data.get("d")).doubleValue();
    }

    public int getValue() {
        long diffSteps = Client.getStep() - s;
        return (int)(diffSteps * d + current);
    }
}
