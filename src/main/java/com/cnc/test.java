package com.cnc;


import com.cnc.api.Authorizator;
import com.cnc.api.CncApiException;
import com.cnc.game.Client;
import com.cnc.game.GameServer;
import com.cnc.model.Player;
import com.cnc.model.Server;

public class test {

    public static void main(String args[]) throws CncApiException {

        Client client = new Client();
        client.setHash(Authorizator.authorize("lworld10@mailinator.com", "qweqwe123"));

        Server s = client.updateServers().get(0);

        client.selectServer(s);
        if (!client.openSession()) {
            System.out.println("dafaq");
        }
        client.updateAllData();
        //cr 165k
        //comb 449
        //supl 180
        System.out.println(client.getPlayer().getCredits().getValue());
        System.out.println(client.getPlayer().getCombatPoint().getValue());
        System.out.println(client.getPlayer().getSupplyPoint().getValue());
        client.close();

    }
}

