package bo.liu.myrx;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017/12/5 0005.
 */

public class RetrofitWrapper {
    private Retrofit mRetrofit;
    private static RetrofitWrapper instance;
    public RetrofitWrapper (String url){
        OkHttpClient client = new OkHttpClient();

        Log.d("MainActivity", "RetrofitWrapper: "+url);
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        mRetrofit = new Retrofit.Builder().baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();

    }

    public  static RetrofitWrapper getInstance(String url){

        if(null == instance){
            synchronized (RetrofitWrapper.class){
                instance = new RetrofitWrapper(url);
            }
        }
        return instance;
    }

    public <T> T create(final Class<T> service) {
        return mRetrofit.create(service);
    }

}
