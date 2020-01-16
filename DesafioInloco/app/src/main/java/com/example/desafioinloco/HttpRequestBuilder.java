package com.example.desafioinloco;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpRequestBuilder {

    public void getJsonFromService (final ViewListener listener){
        OkHttpClient client = new OkHttpClient();

        String url = "http://api.openweathermap.org/data/2.5/find?lat=-7.2428323&lon=-35.9716049&cnt=15&APPID=9df57386cc97f27885cc784e2a583148";

        Request request = new Request.Builder()
                .url(url)
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                 if (response.isSuccessful()){
                     final String myResponse = response.body().string();
                     listener.updateTextFild(myResponse);

                 }
            }
        });

    }


}
