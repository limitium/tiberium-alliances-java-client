package com.cnc.model;

import org.json.simple.JSONObject;

public class Server {
    private String description;
    private String name;
    private String url;
    private boolean online;
    private boolean acceptNewPlayer;
    private long maxPlayers;
    private long lastSeen;
    private long timezone;
    private long faction;
    private long id;

    public Server(JSONObject data) {
        description = (String) data.get("Description");
        name = (String) data.get("Name");
        url = (String) data.get("Url");
        online = (Boolean) data.get("Online");
        acceptNewPlayer = (Boolean) data.get("AcceptNewPlayer");
        maxPlayers = (Long) data.get("MaxPlayers");
        lastSeen = (Long) data.get("LastSeen");
        timezone = (Long) data.get("Timezone");
        faction = (Long) data.get("Faction");
        id = (Long) data.get("Id");
    }

    public long getLastSeen() {
        return lastSeen;
    }

    public long getFaction() {
        return faction;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public boolean isOnline() {
        return online;
    }

    public boolean isAcceptNewPlayer() {
        return acceptNewPlayer;
    }

    public long getMaxPlayers() {
        return maxPlayers;
    }

    public long getTimezone() {
        return timezone;
    }

    public long getId() {
        return id;
    }
}
