package com.cnc.api;


import com.cnc.test;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import java.io.IOException;

public class Api {
    public static final Logger logger = LoggerFactory.getLogger(test.class);

    protected String hash;
    protected String session;

    private String url;
    private Crawler crawler;

    public Api() {
        this.crawler = new Crawler();

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

    public JSONObject getData(String method) {
        return getData(method, new JSONObject(), "Presentation");
    }

    public JSONObject getData(String method, String service) {
        return getData(method, new JSONObject(), service);
    }

    public JSONObject getData(String method, JSONObject params) {
        return getData(method, params, "Presentation");
    }

    public JSONObject getData(String method, JSONObject params, String service) {
        String url;
        synchronized (this) {
            params.put("session", session);
            url = this.url + "/" + service + "/Service.svc/ajaxEndpoint/" + method;
        }
        String response = crawler.postString(url, params.toJSONString());
        try {
            Object parse = (new JSONParser()).parse(response);
            String clazz = parse.getClass().getSimpleName();
            if (!clazz.equals("JSONObject")) {
                JSONObject json = new JSONObject();
                json.put("response", parse);
                parse = json;
            }
            return (JSONObject) parse;
        } catch (ParseException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    public long getTime() {
        return System.currentTimeMillis();
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getHash() {
        return hash;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getSession() {
        return session;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void close() {
        crawler.close();
    }
}
