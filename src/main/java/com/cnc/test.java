package com.cnc;


import com.cnc.api.Api;
import com.cnc.api.Authorizator;
import com.cnc.api.Crawler;
import com.cnc.game.Client;
import com.cnc.game.GameServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class test {
    public static final Logger logger = LoggerFactory.getLogger(test.class);

    public static void main(String args[]) {

//        System.out.println(Authorizator.authorize(new Crawler(), "lworld10@mailinator.com", "qweqwe123"));
        GameServer gameServer = new GameServer();
        gameServer.setHash("ef4b582f-dee6-4b68-ab68-d5d24be4e68d");
//        System.out.println(gameServer.updateHash("lworld10@mailinator.com", "qweqwe123"));
        System.out.println(gameServer.getServers());
//        Client client = new Client(gameServer);
//        client.getServers();
////
//        client.init();
//        client.close();

    }
}

