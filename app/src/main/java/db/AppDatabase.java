package db;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by ayushgarg on 26/11/17.
 */

@Database(entities = {Favourite.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;
    public abstract FavouriteDao favouriteDao();

    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    /**
     * It's will return the same DB instance every time
     * @param context
     * @return instance of database
     */
    public static AppDatabase getAppDatabaseInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "favourite-database").build();
        }
        return instance;
    }

    /**
     * this will destroy the DB instance and call it once the application is killed
     */
    public static void destroyInstance(){
        instance = null;
    }
}
