package com.prueba.pruebaorlando;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.SocketTimeoutException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        callService();
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                callService();
                handler.postDelayed(this, 10000);

            }
        }, 10000);
    }
    public void boton(View view)
    {
        callService();
    }
    private void callService(){
        final Servicio api = Servicio.retrofit.create(Servicio.class);
        final Call<ResponseBody> call = api.ticker();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                //  final TextView textView = (TextView) mRelativeLayout.findViewById(R.id.tvPrueba);
                // showProgress(false);
                Log.i("LIST",""+response.code());
                if (response.code() == 200) {
                    try {
                        String valor =response.body().string();
                        JsonObject obj = new JsonParser().parse(valor).getAsJsonObject();
                        String last=   obj.get("last").getAsString();
                        actualizar(last);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    Log.i("LIST", "ERROR");
                    //textView.setText(contributorResponse.message());
                    //respond=false;
                    actualizar("error");
                }
            }




            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // final TextView textView = (TextView) mRelativeLayout.findViewById(R.id.tvPrueba);
                // textView.setText("Something went wrong: " + t.getMessage());
                if(t instanceof SocketTimeoutException){
                    Log.e("timeout", "servidor no disponible timeOUT");
                    actualizar("error");


                }
                else
                {
                    Log.e("timeout", "servidor no disponible ");
                    actualizar("error");


                }
            }
        });


    }
    private void actualizar(String valor)
    {
        TextView aux =(TextView)findViewById(R.id.textView);
        aux.setText(valor);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(handler!=null)
        {
            handler.removeCallbacksAndMessages(null);
        }
    }
}
