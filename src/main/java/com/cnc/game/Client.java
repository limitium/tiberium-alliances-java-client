package com.cnc.game;

import com.cnc.model.Message;
import com.cnc.model.MessageFolder;
import com.cnc.model.Player;
import org.json.simple.JSONObject;

public class Client {
    private Server server;
    private Player player;
    private MessageFolder inbox;
    private MessageFolder outbox;

    public Client(Server server) {
        server.openSession();
        this.server = server;
        player = new Player();
        inbox = new MessageFolder(MessageFolder.IN_FOLDER);
        outbox = new MessageFolder(MessageFolder.OUT_FOLDER);
    }

    public void close() {
        server.close();
    }

    public void init() {
        player.update(server.getPlayerInfo());
        for (Object fldr : server.igmGetFolders()) {
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
        folder.setCount(server.igmGetMsgCount(folder.getId()));
        for (Object msg : server.igmGetMsgHeader(folder.getId(), 0, 15)) {
            JSONObject message = (JSONObject) msg;
            folder.addMessage(new Message(message));
        }
    }
}
