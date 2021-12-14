package com.example.apod.database;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.apod.model.APODImageData;

import java.util.List;

@Dao
public interface ImagesDAO {

    @Insert
    void saveImages(APODImageData bookmarkedImages);

    @Query("select * from ImageRecord")
    List<APODImageData> fetchImagesList();

    @Query("select title from ImageRecord where date IN(:publishedDate)")
    String seachImagesByPublishedDate(String publishedDate);

    @Query("DELETE FROM ImageRecord WHERE date = :publishedDate")
    void deleteImagesByPublishDate(String publishedDate);

}
