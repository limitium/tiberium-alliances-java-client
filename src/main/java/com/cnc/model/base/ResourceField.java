package com.cnc.model.base;


public class ResourceField {
    private int x;
    private int y;
    private ResourceFieldType type;

    public ResourceField(int x, int y, ResourceFieldType resourceFieldType) {
        this.x = x;
        this.y = y;
        this.type = resourceFieldType;
    }
}
