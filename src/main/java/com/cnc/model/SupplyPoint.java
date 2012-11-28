package com.cnc.model;


public class SupplyPoint {
    private int d;
    private int s;
    private int current;
    private int max;

    public SupplyPoint(int current, int max, int d, int s) {
        this.d = d;
        this.max = max;
        this.s = s;
        this.current = current;
    }
}
