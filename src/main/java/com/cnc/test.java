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
        Crawler c = new Crawler();

        System.out.println(Authorizator.authorize(c, "lworld10@mailinator.com", "qweqwe123"));


//        Api api = new Api(c);
//        api.setHash("2e9796c9-c57c-4ec7-b27c-b29cab9fe523");
//        GameServer gameServer = new GameServer();
//        Client client = new Client(gameServer);
//        client.updateHash("lworld10@mailinator.com", "qweqwe123");
//        client.getServers();
////
//        client.init();
//        client.close();

    }
}

