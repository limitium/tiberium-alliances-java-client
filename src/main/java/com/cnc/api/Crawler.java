package com.cnc.api;

import com.cnc.test;
import org.apache.http.*;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class Crawler {
    public static final Logger logger = LoggerFactory.getLogger(test.class);
    private DefaultHttpClient httpclient;

    public Crawler() {
        //sets up parameters
        HttpParams params = new BasicHttpParams();
        params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
        params.setParameter(CoreConnectionPNames.SO_TIMEOUT, 10000);
        params.setBooleanParameter("http.protocol.expect-continue", false);

        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(params, "utf-8");
        HttpProtocolParams.setUserAgent(params, "Mozilla/5.0 (Linux; U; Android 2.2.1; en-us; Nexus One Build/FRG83)" +
                " AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1");

        //registers schemes for both http and https
        SchemeRegistry registry = new SchemeRegistry();
        registry.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
        SSLContext sslcontext = null;
        try {
            sslcontext = SSLContext.getInstance("TLS");
            sslcontext.init(null, null, null);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        SSLSocketFactory sf = new SSLSocketFactory(
                sslcontext,
                SSLSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        registry.register(new Scheme("https", 443, sf));

        PoolingClientConnectionManager cm = new PoolingClientConnectionManager(registry);
        // Increase max total connection to 200
        cm.setMaxTotal(20);
        // Increase default max connection per route to 20
        cm.setDefaultMaxPerRoute(2);

        httpclient = new DefaultHttpClient(cm, params);
    }

    public Crawler sendGet(String url) {
        try {
            getEntity(url);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return this;
    }

    public String get(String url) {
        try {
            return getEntity(url);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    public String postForm(String url, List<NameValuePair> params) {
        try {
            return postEntity(url, new UrlEncodedFormEntity(params, Consts.UTF_8));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    public String postString(String url, String params) {
        try {
            return postEntity(url, new StringEntity(params, Consts.UTF_8));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    public void addRequiestInspector(HttpRequestInterceptor inspector) {
        httpclient.addRequestInterceptor(inspector);
    }

    public void close() {
        httpclient.getConnectionManager().shutdown();
    }

    private String getEntity(String url) throws IOException {
        HttpGet httpget = new HttpGet(url);
        HttpResponse response = httpclient.execute(httpget, new BasicHttpContext());
        System.out.println(response.getStatusLine());
        String responseString = EntityUtils.toString(response.getEntity());
        httpget.releaseConnection();
        return responseString;
    }

    private String postEntity(String url, HttpEntity entity) throws IOException {
        HttpPost httpost = new HttpPost(url);
        httpost.setEntity(entity);
        HttpResponse response = httpclient.execute(httpost, new BasicHttpContext());
        System.out.println(response.getStatusLine());
        String responseString = EntityUtils.toString(response.getEntity());
        httpost.releaseConnection();
        return responseString;
    }


}
