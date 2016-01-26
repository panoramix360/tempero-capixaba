package com.creativityloop.android.temperocapixaba.util;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by LucasReis on 17/01/2016.
 */
public class RestAPI extends AsyncTask<String, String, String> {

    private RestExecute mRestExecute;

    public RestAPI(RestExecute restExecute) {
        mRestExecute = restExecute;
    }

    @Override
    protected String doInBackground(String... params) {

        String urlString = params[0];
        String jsonResult = "";

        InputStream in = null;

        // HTTP GET
        try {

            URL url = new URL(urlString);

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            in = new BufferedInputStream(urlConnection.getInputStream());

            jsonResult = convertInputStreamToString(in);

        } catch(Exception e) {
            Log.e("RESTAPIERROR", e.getMessage());
            return null;
        }

        return jsonResult;
    }

    protected void onPostExecute(String result) {
        try {
            mRestExecute.execute(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String convertInputStreamToString(InputStream in) throws IOException {
        String result = "";
        String line = "";

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));

        while((line = bufferedReader.readLine()) != null) {
            result += line;
        }

        in.close();

        return result;
    }
}
