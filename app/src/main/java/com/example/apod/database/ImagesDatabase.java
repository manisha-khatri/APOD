package com.example.apod.database;


import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.apod.model.APODImageData;

@Database(entities = {APODImageData.class}, version = 1)
public abstract class ImagesDatabase extends RoomDatabase {

    public static ImagesDatabase dbInstance;

    public abstract ImagesDAO imagesDao();

    public static synchronized ImagesDatabase getInstance(Context context){
        if(dbInstance == null)
        {
                synchronized(ImagesDatabase.class) {
                    dbInstance = Room.databaseBuilder(context.getApplicationContext(), ImagesDatabase.class, "imagesDatabase")
                                .build();
            }
        }
        return dbInstance;
    }



}
