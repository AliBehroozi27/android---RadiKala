package com.example.radikala;

import com.example.radikala.remote.ApiService;
import com.example.radikala.remote.RetrofitClient;

public class ApiUtils {
    public static final String BASE_URL = "https://jsonplaceholder.typicode.com/";

    public static ApiService getApiService() {
        return RetrofitClient.getClient(BASE_URL).create(ApiService.class);
    }
}
