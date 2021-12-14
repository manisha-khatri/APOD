package com.example.apod.database;

import com.example.apod.model.APODImageData;

import java.util.List;

public interface DBRepositoryCallBack {
    public void onFailureResponse(String msg);
    public void onSuccessfulResponse(List<APODImageData> bookmarkedImages);
}
