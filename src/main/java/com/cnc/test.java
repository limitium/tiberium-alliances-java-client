package com.cnc;


import com.cnc.api.Authorizator;
import com.cnc.api.CncApiException;
import com.cnc.game.Client;
import com.cnc.model.Server;
import com.cnc.model.base.City;
import com.cnc.model.resources.CityResourceType;

import java.util.Map;

public class test {

    public static void main(String args[]) throws CncApiException {

        Client client = new Client();
        client.setHash(Authorizator.authorize("lworld10@mailinator.com", "qweqwe123"));
//        client.setHash("6d3f3822-90d2-454e-83b7-a3a80f645b2f");
        client.updateServers();


        Server s = client.updateServers().get(0);

        client.selectServer(s);
        if (!client.openSession()) {
            System.out.println("dafaq");
        }
        client.updateAllData();
        //cr 165k
        //comb 449
        //supl 180
        System.out.print("credits: ");
        System.out.println(client.getPlayer().getCredits().getValue());
        System.out.print("combat: ");
        System.out.println(client.getPlayer().getCombatPoint().getValue());
        System.out.print("supply: ");
        System.out.println(client.getPlayer().getSupplyPoint().getValue());
        System.out.print("research: ");
        System.out.println(client.getPlayer().getResearchPoint());
        for (Map.Entry<Long, City> e : client.getCities().entrySet()) {
            System.out.print("tiberium: ");
            System.out.println(e.getValue().getResource(CityResourceType.TIBERIUM).getValue());
            System.out.print("crystal: ");
            System.out.println(e.getValue().getResource(CityResourceType.CRYSTAL).getValue());
            System.out.print("cretids: ");
            System.out.println(e.getValue().getResource(CityResourceType.CREDITS).getValue());
            System.out.print("power: ");
            System.out.println(e.getValue().getResource(CityResourceType.POWER).getValue());
        }
        client.close();

    }
}

