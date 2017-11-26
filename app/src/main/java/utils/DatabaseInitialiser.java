package utils;

import android.os.AsyncTask;

import db.AppDatabase;
import db.Favourite;

/**
 * Created by ayushgarg on 26/11/17.
 */

public class DatabaseInitialiser {


    public static void populateAsync(AppDatabase database, String photo){
        PopulateAsync async = new PopulateAsync(database, photo);
        async.execute();
    }

    /**
     * This method is use to insert the favourite items to DB
     * @param db
     * @param favourite
     * @return
     */
    private static Favourite addFavourite(final AppDatabase db, Favourite favourite) {
        db.favouriteDao().insertAll(favourite);
        return favourite;
    }

    public static void populateDatabase(AppDatabase database, String photoUrl){
        Favourite favourite = new Favourite();
        favourite.setFavUrl(photoUrl);

        addFavourite(database, favourite);

    }

    private static class PopulateAsync extends AsyncTask<Void, Void, Void>{
        private AppDatabase database;
        private String photoUrl;

        public PopulateAsync(AppDatabase database, String photo) {
            this.database = database;
            this.photoUrl = photo;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            populateDatabase(database, photoUrl);
            return null;
        }
    }
}
