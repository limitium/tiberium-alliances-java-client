package com.cnc.model.resources;

import com.cnc.game.Client;
import org.json.simple.JSONObject;


public class Resource {
    protected Double base;
    protected double delta;
    protected long step;

    public void update(JSONObject data) {
        base = ((Number) data.get("b")).doubleValue();
        step = (Long) data.get("s");
        delta = ((Number) data.get("d")).doubleValue();
    }

    public int getValue() {
        double diffSteps = Client.getStep() - step;
        return (int) (diffSteps * delta + base);
    }
}
