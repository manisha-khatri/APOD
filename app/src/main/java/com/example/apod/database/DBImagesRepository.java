package com.example.apod.database;

import android.app.Application;
import android.os.AsyncTask;

import com.example.apod.model.APODImageData;

import java.util.List;

public class DBImagesRepository {
    public ImagesDAO imagesDAO;

    public DBImagesRepository(Application application){
        ImagesDatabase db = ImagesDatabase.getInstance(application);
        imagesDAO = db.imagesDao();
    }

    public void fetchImagesFromDB(DBRepositoryCallBack dbRepositoryCallBack){
        new fetchImagesListAsyncTask(dbRepositoryCallBack).execute();
    }

    private  class fetchImagesListAsyncTask extends AsyncTask<Void, Void, List<APODImageData>> {
        DBRepositoryCallBack dbRepositoryCallBack;
        public fetchImagesListAsyncTask(DBRepositoryCallBack dbRepositoryCallBack){
            this.dbRepositoryCallBack = dbRepositoryCallBack;
        }
        @Override
        protected List<APODImageData> doInBackground(Void... voids) {
            return imagesDAO.fetchImagesList();
        }
        protected void onPostExecute(List<APODImageData> bookmarkedImagesList){
            if(bookmarkedImagesList.size() > 0)
                dbRepositoryCallBack.onSuccessfulResponse(bookmarkedImagesList);
            else
                dbRepositoryCallBack.onFailureResponse("No images found!");
        }
    }


    public void searchImagesByPublishDate(DBRepositorySearchImagesCallBck dbRepositorySearchImagesCallBck, String publishDate) {
        new searchImagesByPublishedDateAsyncTask(dbRepositorySearchImagesCallBck).execute(publishDate);
    }

    private class searchImagesByPublishedDateAsyncTask extends AsyncTask<String, Void, String> {
        DBRepositorySearchImagesCallBck dbRepositorySearchImagesCallBck;
        public searchImagesByPublishedDateAsyncTask(DBRepositorySearchImagesCallBck dbRepositorySearchImagesCallBck){
            this.dbRepositorySearchImagesCallBck = dbRepositorySearchImagesCallBck;
        }
        @Override
        protected String doInBackground(String... publishedDate) {
            return imagesDAO.seachImagesByPublishedDate(publishedDate[0]);
        }
        protected void onPostExecute(String savedImages){
            if(savedImages != null)
                dbRepositorySearchImagesCallBck.isImagesFound(true);
            else
                dbRepositorySearchImagesCallBck.isImagesFound(false);
        }
    }


    public void deleteImagesByPublishDate(String publishDate) {
        new deleteImagesByPublishDateAsyncTask().execute(publishDate);
    }

    private  class deleteImagesByPublishDateAsyncTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... publishedDate) {
            imagesDAO.deleteImagesByPublishDate(publishedDate[0]);
            return null;
        }
    }

    public void saveImagesInDB(APODImageData apodImageData){
        new saveImagesAsyncTask().execute(apodImageData);
    }

    private  class saveImagesAsyncTask extends AsyncTask<APODImageData, Void, Void> {
        @Override
        protected Void doInBackground(APODImageData... apodImageData) {
            imagesDAO.saveImages(apodImageData[0]);
            return null;
        }
    }

}
