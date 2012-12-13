package com.cnc.game;

import com.cnc.api.Api;
import com.cnc.api.Authorizator;
import com.cnc.api.Crawler;
import com.cnc.model.Server;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class GameServer {
    private final Api api;
    private final Crawler crawler;
    private int sequenceId = 0;
    private int requestId = 0;

    public GameServer() {
        this.crawler = new Crawler();
        this.api = new Api(crawler);
    }

    private JSONObject createRequest(String key, Object val) {
        JSONObject params = new JSONObject();
        params.put(key, val);
        return params;
    }

    public boolean openSession() {
        synchronized (api) {
            api.setSession(api.getHash());
            JSONObject params = createRequest("refId", api.getTime());
            params.put("refId", api.getTime());
            params.put("reset", true);
            params.put("version", -1);
            params.put("platformId", 1);
            JSONObject resp = api.getData("OpenSession", params);
            String session = (String) resp.get("i");
            if (session != null && !session.equals("00000000-0000-0000-0000-000000000000")) {
                requestId = 0;
                sequenceId = 0;
                api.setSession(session);
                return true;
            }
            return false;
        }
    }

    public JSONObject getPlayerInfo() {
        return api.getData("GetPlayerInfo");
    }

    public JSONObject allData() {
        return poll("WC:A\fCTIME:" + api.getTime() + "\fCHAT:\fWORLD:\fGIFT:\fACS:0\fASS:0\fCAT:0\f");
    }

    public JSONArray getServers() {
        synchronized (api) {
            String session = api.getSession();
            String url = api.getUrl();
            api.setSession(api.getHash());
            api.setUrl("https://gamecdnorigin.alliances.commandandconquer.com");
            JSONObject servers = api.getData("GetOriginAccountInfo", "Farm");
            api.setSession(session);
            api.setUrl(url);
            return (JSONArray) servers.get("Servers");
        }
    }

    public JSONObject poll(String request) {
        JSONObject params;
        synchronized (this) {
            params = createRequest("requests", request);
            params.put("requestid", requestId++);
            params.put("sequenceid", sequenceId++);

        }
        return api.getData("Poll", params);
    }

    public void close() {
        api.close();
    }

    public boolean checkPlayerExist(String name) {
        return (Boolean) api.getData("CheckPlayerExist", createRequest("name", name)).get("response");
    }

    public JSONArray igmGetFolders() {
        return (JSONArray) api.getData("IGMGetFolders").get("response");
    }

    public long igmGetMsgCount(long folderId) {
        return (Long) api.getData("IGMGetMsgCount", createRequest("folderId", folderId)).get("response");
    }

    public JSONArray igmGetMsgHeader(long folderId, int skip, int limit) {
        JSONObject params = createRequest("folder", folderId);
        params.put("skip", skip);
        params.put("take", limit);
        params.put("ascending", false);
        params.put("sortColumn", 1);
        return (JSONArray) api.getData("IGMGetMsgHeader", params).get("response");
    }

    public String igmGetMsg(int mailId) {
        return (String) api.getData("IGMGetMsg", createRequest("mailId", mailId)).get("response");
    }

    public boolean igmSetReadFlag(Integer[] mailIds) {
        JSONObject params = createRequest("flag", true);
        params.put("msgIds", mailIds);
        return (Boolean) api.getData("IGMSetReadFlag", params).get("response");
    }

    public int igmBulkDeleteMsg(int folderId, Integer[] mailIds) {
        JSONObject params = createRequest("a", false);
        params.put("f", folderId);
        params.put("ids", mailIds);
        return (Integer) api.getData("IGMBulkDeleteMsg", params).get("response");
    }

    public Integer[] igmBulkSendMsg(String subject, String players, String body, String alliances) {
        JSONObject params = createRequest("alliances", alliances);
        params.put("subject", subject);
        params.put("body", body);
        params.put("players", players);
        return (Integer[]) api.getData("IGMBulkDeleteMsg", params).get("response");
    }

    public void selectServer(Server server) {
        api.setUrl(server.getUrl());
    }

    public String updateHash(String username, String password) {
        String hash = Authorizator.authorize(this.crawler, username, password);
        api.setHash(hash);
        return hash;
    }
}