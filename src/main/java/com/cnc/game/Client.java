package com.cnc.game;

import com.cnc.api.CncApiException;
import com.cnc.model.Alliance;
import com.cnc.model.Player;
import com.cnc.model.Server;
import com.cnc.model.base.City;
import com.cnc.model.communication.Message;
import com.cnc.model.communication.MessageFolder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Client {
    private GameServer gameServer;
    private Player player;
    private Alliance alliance;
    private HashMap<Long, City> cities;
    private MessageFolder inbox;
    private MessageFolder outbox;
    private ArrayList<Server> servers;
    private static long timeD = 0;
    private static long timeS = 0;
    private static long timeR = 0;

    public Client() {
        gameServer = new GameServer();
        cities = new HashMap<Long, City>();
        servers = new ArrayList<Server>();
        player = new Player();
        alliance = new Alliance();
        inbox = new MessageFolder(MessageFolder.IN_FOLDER);
        outbox = new MessageFolder(MessageFolder.OUT_FOLDER);

    }

    public void close() {
        gameServer.close();
    }

    public Player updatePlayerData() throws CncApiException {
        player.update(gameServer.getPlayerInfo());
        return player;
    }

    private void updateMessageInfo() throws CncApiException {
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

    private void updateIGMFolder(MessageFolder folder, JSONObject data) throws CncApiException {
        folder.update(data);
        folder.setCount(gameServer.igmGetMsgCount(folder.getId()));
        for (Object msg : gameServer.igmGetMsgHeader(folder.getId(), 0, 15)) {
            JSONObject message = (JSONObject) msg;
            folder.addMessage(new Message(message));
        }
    }

    public ArrayList<Server> updateServers() throws CncApiException {
        servers.clear();
        JSONArray responseServers = gameServer.getServers();
        if (responseServers == null) {
            throw new CncApiException("Empty servers list.");
        }
        for (Object srv : responseServers) {
            Server server = new Server((JSONObject) srv);
            if (server.getLastSeen() > 0) {
                servers.add(server);
            }
        }
        return servers;
    }

    public void updateAllData() throws CncApiException {
        for (Object o : gameServer.allData()) {
            JSONObject containerData = (JSONObject) o;
            String type = containerData.get("t").toString();

            if (type.equalsIgnoreCase("CITYSUPPORT")) {
                continue;
            }

            JSONObject data = (JSONObject) containerData.get("d");
            if (type.equalsIgnoreCase("TIME")) {
                timeD = (Long) data.get("d");
                timeS = (Long) data.get("s");
                timeR = (Long) data.get("r");
            }
            if (type.equalsIgnoreCase("CITIES")) {
                cities.clear();
                for (Object co : (JSONArray) data.get("c")) {
                    City city = new City();
                    city.update((JSONObject) co);
                    cities.put(city.getId(), city);
                }
            }
            if (type.equalsIgnoreCase("PLAYER")) {
                player.update(data);
            }
            if (type.equalsIgnoreCase("ALLIANCE")) {
                alliance.update(data);
            }
        }
    }

    public void selectServer(Server server) {
        gameServer.selectServer(server);
    }

    public String updateHash(String username, String password) throws CncApiException {
        return gameServer.updateHash(username, password);
    }

    public boolean openSession() throws CncApiException {
        return gameServer.openSession();
    }

    public void setHash(String hash) {
        gameServer.setHash(hash);
    }

    public Player getPlayer() {
        return player;
    }

    public HashMap<Long, City> getCities() {
        return cities;
    }

    public static double getStep() {
        return Math.floor((System.currentTimeMillis() - timeD - timeR) / timeS);
    }

}
