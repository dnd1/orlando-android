package com.prueba.pruebaorlando;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by jofl on 08/09/17.
 */

public interface Servicio {

    @GET("ticker")
    Call<ResponseBody> ticker();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://www.bitstamp.net/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
