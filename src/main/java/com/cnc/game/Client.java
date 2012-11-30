package com.cnc.game;

import com.cnc.model.Message;
import com.cnc.model.MessageFolder;
import com.cnc.model.Player;
import com.cnc.model.Server;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class Client {
    private GameServer gameServer;
    private Player player;
    private MessageFolder inbox;
    private MessageFolder outbox;

    public Client(GameServer gameServer) {
        this.gameServer = gameServer;
        player = new Player();
        inbox = new MessageFolder(MessageFolder.IN_FOLDER);
        outbox = new MessageFolder(MessageFolder.OUT_FOLDER);

    }

    public void close() {
        gameServer.close();
    }

    public void init() {
        player.update(gameServer.getPlayerInfo());
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

    public ArrayList<Server> getServers() {
        ArrayList<Server> servers = new ArrayList<Server>();
        for (Object srv : gameServer.getServers()) {
            Server server = new Server((JSONObject) srv);
            if (server.getLastSeen() > 0) {
                servers.add(server);
            }
        }
        return servers;
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
}
