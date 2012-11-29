package com.cnc;


import com.cnc.api.Api;
import com.cnc.game.Client;
import com.cnc.game.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class test {
    public static final Logger logger = LoggerFactory.getLogger(test.class);

    public static void main(String args[]) {

//        String hash = Authorizator.authorize("lworld10@mailinator.com", "qweqwe123");
//        System.out.println(hash);
//        Api api = new Api(hash);

        Api api = new Api("5270ea7b-c559-47c0-b336-875c662f4a97");
        api.setUrl("https://prodgame02.alliances.commandandconquer.com/24");

        Server server = new Server(api);
        Client client = new Client(server);

        client.init();
        client.close();

    }
}

