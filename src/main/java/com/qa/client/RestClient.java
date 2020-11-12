package com.qa.client;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class RestClient {

    // 1. Get method without headers
    public CloseableHttpResponse get(String url) throws IOException {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
        return httpResponse;
    }

    // 2. GET Method with headers
    public CloseableHttpResponse get(String url, HashMap<String, String> headersMap) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);

        for (Map.Entry<String, String> entry : headersMap.entrySet()) {
            httpGet.addHeader(entry.getKey(), entry.getValue());
        }
        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);

        return httpResponse;
    }

    // 3. POST Method
    public CloseableHttpResponse post(String url, String entityString, HashMap<String, String> headerMap) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url); // http post request
        httpPost.setEntity(new StringEntity(entityString)); // for payload

        // for headers:
        for (Map.Entry<String,String> entry : headerMap.entrySet()){
            httpPost.addHeader(entry.getKey(), entry.getValue());
        }
       CloseableHttpResponse httpResponse= httpClient.execute(httpPost);

         return httpResponse;
    }
}

