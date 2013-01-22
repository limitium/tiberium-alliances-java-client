package com.cnc.api;

import org.apache.http.*;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.*;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class Crawler {
    public static final Logger logger = LoggerFactory.getLogger(Crawler.class);
    private DefaultHttpClient httpclient;

    public Crawler() {
        //sets up parameters
        HttpParams params = new BasicHttpParams();

        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setUseExpectContinue(params, true);
        HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
        HttpProtocolParams.setUserAgent(params, "Mozilla/5.0 (Linux; U; Android 2.2.1; en-us; Nexus One Build/FRG83)" +
                " AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1");

        ConnManagerParams.setMaxTotalConnections(params, 20);

        HttpConnectionParams.setConnectionTimeout(params, 5000);
        HttpConnectionParams.setSoTimeout(params, 10000);

        //registers schemes for both http and https
        SchemeRegistry registry = new SchemeRegistry();
        registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        SSLSocketFactory socketFactory = SSLSocketFactory.getSocketFactory();
        socketFactory.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        registry.register(new Scheme("https", socketFactory, 443));

        ThreadSafeClientConnManager cm = new ThreadSafeClientConnManager(params, registry);


        httpclient = new DefaultHttpClient(cm, params);
    }

    public Crawler sendGet(String url) throws IOException {
        getEntity(url);
        return this;
    }

    public String get(String url) throws IOException {
        return getEntity(url);
    }

    public String postForm(String url, List<NameValuePair> params) throws IOException {
        return postEntity(url, new UrlEncodedFormEntity(params));
    }

    public String postString(String url, String params) throws IOException {
        return postEntity(url, new StringEntity(params));
    }

    public void addRequiestInspector(HttpRequestInterceptor inspector) {
        httpclient.addRequestInterceptor(inspector);
    }

    public void close() {
        httpclient.getConnectionManager().shutdown();
    }

    private String getEntity(String url) throws IOException {
        logger.debug(url);
        HttpGet httpget = new HttpGet(url);
        HttpResponse response = httpclient.execute(httpget, new BasicHttpContext());
        logger.debug(response.getStatusLine().toString());
        HttpEntity responseEntity = response.getEntity();
        String responseString = EntityUtils.toString(responseEntity);
        responseEntity.consumeContent();
        return responseString;
    }

    private String postEntity(String url, HttpEntity entity) throws IOException {
        logger.debug(url);
        HttpPost httpost = new HttpPost(url);
        httpost.setEntity(entity);
        HttpResponse response = httpclient.execute(httpost, new BasicHttpContext());
        logger.debug(response.getStatusLine().toString());
        HttpEntity responseEntity = response.getEntity();
        String responseString = EntityUtils.toString(responseEntity);
        responseEntity.consumeContent();
        return responseString;
    }


}
