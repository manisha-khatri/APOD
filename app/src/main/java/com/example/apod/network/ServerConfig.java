package com.example.apod.network;


public class ServerConfig {

    public static final String BASE_URL = "https://api.nasa.gov/";
    public static final String API_KEY = "ELIoTMpVbxCGc2qdmmrpL32SXchL2kQdqcQdiJZV";

    //timeout
    public static final int REQUEST_TIMEOUT_OKHTTP = 120;
    public static final int REQUEST_TIMEOUT_OKHTTP_EXTRA = 3000;
    public static final int REQUEST_TIMEOUT_RX = 20000;
    public static final int RX_RETRY_COUNT = 5;

    public static String getBaseUrl() {
        return BASE_URL;
    }


}
