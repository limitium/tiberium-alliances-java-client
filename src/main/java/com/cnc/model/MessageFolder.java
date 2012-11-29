package com.cnc.model;


import org.json.simple.JSONObject;

import java.util.ArrayList;

public class MessageFolder {
    public static final String IN_FOLDER = "@In";
    public static final String OUT_FOLDER = "@Out";
    private Long id;
    private String type;
    private Long count;
    private ArrayList<Message> messages;

    public MessageFolder(String type) {
        this.type = type;
        clear();
    }

    public void addMessage(Message message) {
        System.out.println(message.getHeader());
        this.messages.add(message);
    }

    private void clear() {
        this.messages = new ArrayList<Message>();
    }

    public void update(JSONObject data) {
        if (!data.get("n").equals(this.type)) {
            System.out.println("wtf?! folder");
            return;
        }
        id = (Long) data.get("i");
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Long getId() {
        return id;
    }
}
