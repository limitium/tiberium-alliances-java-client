package com.cnc;


import com.cnc.api.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class test {
    public static final Logger logger = LoggerFactory.getLogger(test.class);

    public static void main(String args[]) {

//        String hash = Authorizator.authorize("lwelt2@mailinator.com", "qweqwe123");
        Api api = new Api("610d51dc-c099-43ee-a8ae-c1510570f992");
        api.setUrl("https://prodgame08.alliances.commandandconquer.com/16");
        if (api.openSession()) {
            System.out.println(api.getServerInfo().get("n"));
        } else {
            System.out.println("not ok");
        }

    }
}

