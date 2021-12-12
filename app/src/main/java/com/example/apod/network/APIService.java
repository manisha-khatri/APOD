package com.example.apod.network;

import com.example.apod.model.APODImageData;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {


    @GET("planetary/apod")
    Observable<APODImageData> getAPODData(
            @Query("date")String date,
            @Query("api_key") String key
    );

    //api_key=ELIoTMpVbxCGc2qdmmrpL32SXchL2kQdqcQdiJZV
}
