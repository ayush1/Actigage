package db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by ayushgarg on 26/11/17.
 */

@Dao
public interface FavouriteDao {

    @Query("SELECT * FROM favourite")
    List<Favourite> getAll();

    @Query("SELECT COUNT(*) FROM favourite")
    int countFavourite();

    @Insert
    void insertAll(Favourite... favourites);

    @Delete
    void delete(Favourite favourite);

}
