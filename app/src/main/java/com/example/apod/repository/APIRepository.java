package com.example.apod.repository;

import com.example.apod.model.APODImageData;
import com.example.apod.network.APIManager;
import com.example.apod.network.ServerConfig;
import java.util.concurrent.TimeUnit;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class APIRepository {

    private static final APIRepository apiRepoInstance = new APIRepository();
    APIManager.APIService apiClient;

    public static APIRepository getInstance() {
        return apiRepoInstance;
    }

    private APIRepository() {
        apiClient = APIManager.getInstance().getApiClient();
    }

    public Observable<APODImageData> fetchAPODData(String date) {
        return apiClient.getAPODData(date)
                .timeout(ServerConfig.REQUEST_TIMEOUT_RX, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io());
    }
}
