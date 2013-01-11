package com.cnc.api;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Authorizator {
    public static final Logger logger = LoggerFactory.getLogger(Authorizator.class);

    public static String authorize(String username, String password) {
        return Authorizator.authorize(username, password, null);
    }

    public static String authorize(String username, String password, Progress progress) {
        Crawler c = new Crawler();
        Authorizator.step(1, "Init session", progress);
        c.sendGet("https://www.tiberiumalliances.com/home");
        Authorizator.step(2, "Retrieve login", progress);
        c.sendGet("https://www.tiberiumalliances.com/login/auth");

        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("_web_remember_me", "username"));
        nvps.add(new BasicNameValuePair("spring-security-redirect", "password"));
        nvps.add(new BasicNameValuePair("timezone", "password"));
        nvps.add(new BasicNameValuePair("id", "password"));
        nvps.add(new BasicNameValuePair("j_username", username));
        nvps.add(new BasicNameValuePair("j_password", password));

        Authorizator.step(3, "Send data", progress);
        c.postForm("https://www.tiberiumalliances.com/j_security_check", nvps);

        Authorizator.step(4, "Get launcher", progress);
        String response = c.get("https://www.tiberiumalliances.com/game/launch");
        if (response != null) {
            Authorizator.step(5, "Take token", progress);
            Pattern pattern = Pattern.compile("<input type=\"hidden\" name=\"sessionId\" value=\"(.*)?\" \\/>");
            Matcher matcher = pattern.matcher(response);

            while (matcher.find()) {
                String hash = matcher.group(1);
                logger.debug(hash);
                c.close();
                return hash;
            }
        }
        logger.warn("Authorization failed for " + username + "|" + password);
        return null;
    }

    private static void step(int step, String msg, Progress progress) {
        if (progress != null) {
            progress.onStep(step, msg);
        }
    }

    public interface Progress {
        public void onStep(int step, String msg);
    }
}
