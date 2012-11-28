package com.cnc.api;

import com.cnc.test;
import org.apache.http.*;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class Crawler {
    public static final Logger logger = LoggerFactory.getLogger(test.class);
    private DefaultHttpClient httpclient;

    public Crawler() {
        httpclient = new DefaultHttpClient();
    }

    public Crawler sendGet(String url) {
        try {
            HttpEntity entity = getEntity(url);
            EntityUtils.consume(entity);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return this;
    }

    public String get(String url) {
        try {
            HttpEntity entity = getEntity(url);
            return EntityUtils.toString(entity);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    private HttpEntity getEntity(String url) throws IOException {
        HttpGet httpget = new HttpGet(url);
        HttpResponse response = httpclient.execute(httpget);
        return response.getEntity();
    }

    private HttpEntity postEntity(String url, HttpEntity entity) throws IOException {
        HttpPost httpost = new HttpPost(url);
        httpost.setEntity(entity);

        HttpResponse response = httpclient.execute(httpost);
        System.out.println(url);
        System.out.println(response.getStatusLine());
        return response.getEntity();
    }

    public String postForm(String url, List<NameValuePair> params) {
        try {
            HttpEntity entity = postEntity(url, new UrlEncodedFormEntity(params, Consts.UTF_8));
            return EntityUtils.toString(entity);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    public String postString(String url, String params) {
        try {
            HttpEntity entity = postEntity(url, new StringEntity(params, Consts.UTF_8));
            return EntityUtils.toString(entity);
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


}
