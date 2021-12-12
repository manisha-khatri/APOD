package com.example.apod.repository;

import com.example.apod.model.APODImageData;
import com.example.apod.network.APIService;
import com.example.apod.network.APIClient;
import com.example.apod.network.ServerConfig;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class APIRepository {

    private static final APIRepository apiRepoInstance = new APIRepository();
    APIService apiService;

    public static APIRepository getInstance() {
        return apiRepoInstance;
    }

    private APIRepository() {
        apiService = APIClient.getRetrofitInstance().create(APIService.class);
    }

    public Observable<APODImageData> fetchAPODData(String date) {
        return apiService.getAPODData(date, ServerConfig.API_KEY)
                .subscribeOn(Schedulers.io());
    }
}
