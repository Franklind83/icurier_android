package com.dev.todos.Network;


import com.dev.todos.Url.WebService;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit retrofit = null;

    static Retrofit getClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);


        OkHttpClient client = new OkHttpClient.Builder()

                .readTimeout(30, TimeUnit.MINUTES)
                .writeTimeout(30, TimeUnit.MINUTES)
                .connectTimeout(30, TimeUnit.MINUTES)
                .addInterceptor(interceptor).build();

        retrofit = new Retrofit.Builder()
                .baseUrl(WebService.BASE_URL)

                .addConverterFactory(GsonConverterFactory.create())
                .client(client).build();

        return retrofit;
    }


    public static APIInterface getService() {
        return getClient().create(APIInterface.class);
    }

}
