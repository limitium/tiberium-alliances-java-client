package com.cnc.api;


import com.cnc.test;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.protocol.HttpContext;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Api {
    public static final Logger logger = LoggerFactory.getLogger(test.class);
    private String hash;
    private String session;
    private int sequenceId = 0;
    private int requestId = 0;


    private String url;
    private Crawler crawler;
    private JSONParser parser;

    public Api(String hash) {
        this.hash = hash;
        this.crawler = new Crawler();
        this.parser = new JSONParser();

        this.crawler.addRequiestInspector(new HttpRequestInterceptor() {
            public void process(
                    final HttpRequest request,
                    final HttpContext context) throws HttpException, IOException {
                if (!request.containsHeader("X-Qooxdoo-Response-Type")) {
                    request.addHeader("X-Qooxdoo-Response-Type", "application/json");
                }
                request.removeHeaders("Content-Type");
                request.addHeader("Content-Type", "application/json; charset=UTF-8");
            }

        });
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public JSONObject getServers() {
        String session = this.session;
        String url = this.url;
        this.session = hash;
        this.url = "https://gamecdnorigin.alliances.commandandconquer.com";
        JSONObject servers = getData("GetOriginAccountInfo", "Farm");
        this.session = session;
        this.url = url;
        return servers;
    }

    private JSONObject getData(String method) {
        return getData(method, new JSONObject(), "Presentation");
    }

    private JSONObject getData(String method, String service) {
        return getData(method, new JSONObject(), service);
    }

    private JSONObject getData(String method, JSONObject params) {
        return getData(method, params, "Presentation");
    }

    private JSONObject getData(String method, JSONObject params, String service) {
        params.put("session", session);
        String response = crawler.postString(url + "/" + service + "/Service.svc/ajaxEndpoint/" + method, params.toJSONString());

        try {
            System.out.println(response);
            return (JSONObject) parser.parse(response);
        } catch (ParseException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    public boolean openSession() {
        this.session = hash;
        JSONObject params = new JSONObject();
        params.put("refId", getTime());
        params.put("reset", true);
        params.put("version", -1);
        params.put("platformId", 1);
        JSONObject resp = getData("OpenSession", params);
        String session = (String) resp.get("i");
        if (session != null && session != "00000000-0000-0000-0000-000000000000") {
            this.session = session;
            return true;
        }
        return false;
    }

    public JSONObject getServerInfo() {
        return getData("GetServerInfo");
    }

    public JSONObject getPlayerInfo() {
        return getData("GetPlayerInfo");
    }

    public JSONObject allData() {
        return poll("WC:A\fCTIME:" + getTime() + "\fCHAT:\fWORLD:\fGIFT:\fACS:0\fASS:0\fCAT:0\f");
    }

    public JSONObject poll(String request) {
        JSONObject resp = getData("Poll", request);
        sequenceId++;
        requestId++;
        return resp;
    }

    private long getTime() {
        return System.currentTimeMillis();
    }
}
