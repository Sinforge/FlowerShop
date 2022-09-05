package ru.sinforge.mywebapplication.Services;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import okhttp3.ResponseBody;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.sinforge.mywebapplication.Models.HoroscopeModel;

import javax.xml.crypto.Data;
import java.io.IOException;



public class HoroscopeAPI {
    private ResponseEntity<HoroscopeModel> response;
    public HoroscopeAPI(String SignFromQuery, String DayFromQuery) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://sameer-kumar-aztro-v1.p.rapidapi.com/?sign=" + SignFromQuery +"&day=" + DayFromQuery;
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-RapidAPI-Key", "8f09d41d52msh4c295a4f3a130fdp12e19bjsn31fa4a41e2c0");
        headers.set("X-RapidAPI-Host", "sameer-kumar-aztro-v1.p.rapidapi.com");


        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        response = restTemplate.exchange(
                url, HttpMethod.POST, requestEntity, HoroscopeModel.class, new ParameterizedTypeReference<HoroscopeModel>() {});
    }

    public  String GetInfoFromAPI() {
        return response.getBody().toString();
    }

}
