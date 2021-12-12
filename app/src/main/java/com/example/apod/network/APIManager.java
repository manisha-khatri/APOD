package com.example.apod.network;


import com.example.apod.model.APODImageData;
import com.example.apod.utils.Constants;
import org.jetbrains.annotations.NotNull;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import io.reactivex.Observable;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class APIManager {

    private static APIManager apiManager;
    private APIService mApiService;

    public static APIManager getInstance() {
        if (apiManager == null) {
            apiManager = new APIManager();
        }
        return apiManager;
    }

    public APIService getApiClient() {
        if (mApiService == null) {
            Retrofit restAdapter = new Retrofit.Builder()
                    .baseUrl(ServerConfig.getBaseUrl())
                    //.client(getOkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    //.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            mApiService = restAdapter.create(APIService.class);
        }
        return mApiService;
    }


    /*
     .baseUrl(NetworkConstants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
    *
    */

    private OkHttpClient getOkHttpClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.readTimeout(ServerConfig.REQUEST_TIMEOUT_OKHTTP, TimeUnit.SECONDS);
        httpClient.connectTimeout(ServerConfig.REQUEST_TIMEOUT_OKHTTP, TimeUnit.SECONDS);
        httpClient.retryOnConnectionFailure(true);
        httpClient.addInterceptor(logging);
        httpClient.addNetworkInterceptor(getHeader());
        return httpClient.build();
    }

    private Interceptor getHeader() {
        return chain -> {
            Request original = chain.request();
            Request.Builder requestBuilder = original.newBuilder();
            requestBuilder.addHeader(Constants.API_KEY, ServerConfig.API_KEY);
            Request request = requestBuilder.build();
            return chain.proceed(request);
        };
    }


    public interface APIService {

        @GET("planetary/apod")
        Observable<APODImageData> getAPODData(
                @Query("date")String date
        );

    }

}
