package com.cnc.model.messaging;


import org.json.simple.JSONObject;

public class Message {
    private long id;
    private boolean isRead;
    private String from;
    private String header;
    private String body;
    private long time;

    public Message(JSONObject data) {
        id = (Long) data.get("i");
        from = (String) data.get("f");
        header = (String) data.get("s");
        time = (Long) data.get("d");
        isRead = (Boolean) data.get("r");
    }

    public String getHeader() {
        return header;
    }
}
