package com.cnc.game;

import com.cnc.model.base.City;
import com.cnc.model.communication.Message;
import com.cnc.model.communication.MessageFolder;
import com.cnc.model.Player;
import com.cnc.model.Server;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Client {
    private GameServer gameServer;
    private Player player;
    private HashMap<Long, City> cities;
    private MessageFolder inbox;
    private MessageFolder outbox;
    private ArrayList<Server> servers;
    private static long deltaTime;

    public Client(GameServer gameServer) {
        this.gameServer = gameServer;
        cities = new HashMap<Long, City>();
        player = new Player();
        inbox = new MessageFolder(MessageFolder.IN_FOLDER);
        outbox = new MessageFolder(MessageFolder.OUT_FOLDER);

    }

    public void close() {
        gameServer.close();
    }

    public Player updatePlayerData() {
        player.update(gameServer.getPlayerInfo());
        return player;
    }

    private void updateMessageInfo() {
        for (Object fldr : gameServer.igmGetFolders()) {
            JSONObject folder = (JSONObject) fldr;

            if (folder.get("n").equals(MessageFolder.IN_FOLDER)) {
                updateIGMFolder(inbox, folder);
            }
            if (folder.get("n").equals(MessageFolder.OUT_FOLDER)) {
                updateIGMFolder(outbox, folder);
            }
        }
    }

    private void updateIGMFolder(MessageFolder folder, JSONObject data) {
        folder.update(data);
        folder.setCount(gameServer.igmGetMsgCount(folder.getId()));
        for (Object msg : gameServer.igmGetMsgHeader(folder.getId(), 0, 15)) {
            JSONObject message = (JSONObject) msg;
            folder.addMessage(new Message(message));
        }
    }

    public ArrayList<Server> updateServers() {
        servers = new ArrayList<Server>();
        for (Object srv : gameServer.getServers()) {
            Server server = new Server((JSONObject) srv);
            if (server.getLastSeen() > 0) {
                servers.add(server);
            }
        }
        return servers;
    }

    public void updateAllData() {
        for (Object o : gameServer.allData()) {
            JSONObject containerData = (JSONObject) o;
            String type = containerData.get("t").toString();

            if (type.equalsIgnoreCase("CITYSUPPORT")) {
                continue;
            }

            JSONObject data = (JSONObject) containerData.get("d");
            if (type.equalsIgnoreCase("TIME")) {
//                deltaTime = System.currentTimeMillis() - (Long) data.get("r");
                deltaTime = (Long) data.get("d");
            }
            if (type.equalsIgnoreCase("CITIES")) {
                for (Object co : (JSONArray) data.get("c")) {
                    City city = new City();
                    city.update((JSONObject) co);
                    cities.put(city.getId(), city);
                }
            }
        }
    }

    public void selectServer(Server server) {
        gameServer.selectServer(server);
    }

    public String updateHash(String username, String password) {
        return gameServer.updateHash(username, password);
    }

    public boolean openSession() {
        return gameServer.openSession();
    }

    public Player getPlayer() {
        return player;
    }

    public static long getStep() {
        return System.currentTimeMillis() - deltaTime;
    }
}
