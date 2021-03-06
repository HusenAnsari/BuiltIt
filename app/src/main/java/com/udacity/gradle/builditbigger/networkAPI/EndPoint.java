package com.udacity.gradle.builditbigger.networkAPI;

import android.os.AsyncTask;
import android.util.Log;

import com.example.husenansari.myapplication.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;


public class EndPoint extends AsyncTask<onJokeReceived, Void, String> {
    private static MyApi myApiService = null;
    private onJokeReceived listener;
    private String ROOT_URL = "https://builditbigger-146605.appspot.com/_ah/api/";
    @Override
    protected String doInBackground(onJokeReceived... params) {
        if(myApiService == null) {
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl(ROOT_URL)
            .setGoogleClientRequestInitializer(
                    new GoogleClientRequestInitializer() {
                @Override
                public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                    abstractGoogleClientRequest.setDisableGZipContent(true);
                }
            });
            myApiService = builder.build();
        }

        listener = params[0];

        try {
            String name =  myApiService.tellJoke().execute().getData();
            //if(name!=null)
                Log.d("hello",name);
            return name;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String result) {
        listener.OnJokeReceivedListener(result);
    }
}
