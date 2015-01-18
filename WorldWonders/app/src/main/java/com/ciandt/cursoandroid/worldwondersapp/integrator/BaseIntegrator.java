package com.ciandt.cursoandroid.worldwondersapp.integrator;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by Wellington on 04/12/2014.
 */
public class BaseIntegrator {

    public  String doGetRequest(String protocol, String host, String path) {

        String responseStr = null;

        try {

            URI uri = URIUtils.createURI(protocol, host, 0, path, null, null);
            HttpGet get = new HttpGet(uri);
            HttpClient client = new DefaultHttpClient();
            HttpResponse response;

            response = client.execute(get);

            HttpEntity entity = response.getEntity();

            responseStr = EntityUtils.toString(entity);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return responseStr;

    }
}
