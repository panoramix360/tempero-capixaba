package com.creativityloop.android.temperocapixaba.fetchr;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by LucasReis on 10/02/2016.
 */
public class Fetchr {

    private static final String TAG = "Fetchr";
    public static final String URL_BASE = "http://www.temperocapixaba.com.br/rest/v1/";

    public Fetchr() {

    }

    public byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try {

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();

            if(connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() + ": with " + urlSpec);
            }

            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }

            out.close();
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }

    public String getUrlString(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }

    public byte[] getUrlBytesByInputStream(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        int bytesRead = 0;
        byte[] buffer = new byte[1024];
        while((bytesRead = in.read(buffer)) > 0) {
            out.write(buffer, 0, bytesRead);
        }

        out.close();
        return out.toByteArray();
    }

    public String postData(String urlSpec, String data) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        String response = "";

        try {
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setFixedLengthStreamingMode(data.getBytes().length);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            OutputStream os = new BufferedOutputStream(connection.getOutputStream());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(data);
            writer.flush();

            if(connection.getErrorStream() != null) {
                response = new String(getUrlBytesByInputStream(connection.getErrorStream()));
                throw new Exception("Error postando data" + response);
            } else if(connection.getInputStream() != null) {
                response = new String(getUrlBytesByInputStream(connection.getInputStream()));
            }

            writer.close();
            os.close();

            connection.connect();
        } catch(Exception e) {
            Log.e(TAG, "Error posting data");
        } finally {
            connection.disconnect();
        }

        return response;
    }
}
