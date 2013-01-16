package com.cnc;


import com.cnc.api.Authorizator;
import com.cnc.game.Client;
import com.cnc.game.GameServer;
import com.cnc.model.Player;
import com.cnc.model.Server;

public class test {

    public static void main(String args[]) {

        GameServer gameServer = new GameServer();
        gameServer.setHash(Authorizator.authorize("lworld10@mailinator.com", "qweqwe123"));
        Client client = new Client(gameServer);
        Server s = client.getServers().get(0);
        client.selectServer(s);
        if (!client.openSession()) {
            System.out.println("dafaq");
        }

        Player player = client.updatePlayerData();
        client.close();

    }
}

