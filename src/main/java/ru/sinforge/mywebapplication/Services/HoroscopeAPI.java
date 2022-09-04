package ru.sinforge.mywebapplication.Services;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import okhttp3.ResponseBody;

import javax.xml.crypto.Data;
import java.io.IOException;



public class HoroscopeAPI {
    private Request  request;
    private Response response;
    private OkHttpClient client;
    public HoroscopeAPI() {
        client = new OkHttpClient();

        request = new Request.Builder()
                .url("https://sameer-kumar-aztro-v1.p.rapidapi.com/?sign=aquarius&day=today")
                .post(null)
                .addHeader("X-RapidAPI-Key", "8f09d41d52msh4c295a4f3a130fdp12e19bjsn31fa4a41e2c0")
                .addHeader("X-RapidAPI-Host", "sameer-kumar-aztro-v1.p.rapidapi.com")
                .build();


    }

    public  String GetInfoFromAPI() {
        Response response = null;
        try {
            response = client.newCall(request).execute();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return response.body().toString();
    }

}
