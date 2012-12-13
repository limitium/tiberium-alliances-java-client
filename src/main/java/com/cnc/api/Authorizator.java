package com.cnc.api;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Authorizator {
    public static final Logger logger = Logger.getLogger(Authorizator.class);

    public static String authorize(String username, String password) {
        Crawler c = new Crawler();
        c
                .sendGet("https://www.tiberiumalliances.com/home")
                .sendGet("https://www.tiberiumalliances.com/login/auth");

        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("_web_remember_me", "username"));
        nvps.add(new BasicNameValuePair("spring-security-redirect", "password"));
        nvps.add(new BasicNameValuePair("timezone", "password"));
        nvps.add(new BasicNameValuePair("id", "password"));
        nvps.add(new BasicNameValuePair("j_username", username));
        nvps.add(new BasicNameValuePair("j_password", password));

        c.postForm("https://www.tiberiumalliances.com/j_security_check", nvps);

        String response = c.get("https://www.tiberiumalliances.com/game/launch");
        Pattern pattern = Pattern.compile("<input type=\"hidden\" name=\"sessionId\" value=\"(.*)?\" \\/>");
        Matcher matcher = pattern.matcher(response);

        while (matcher.find()) {
            String hash = matcher.group(1);
            logger.info(hash);
            c.close();
            return hash;
        }
        logger.warn("Authorization failed.");
        return null;
    }
}
