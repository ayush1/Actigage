package db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by ayushgarg on 26/11/17.
 */

@Entity(tableName = "favourite")
public class Favourite {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "fav_url")
    private String favUrl;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getFavUrl() {
        return favUrl;
    }

    public void setFavUrl(String favUrl) {
        this.favUrl = favUrl;
    }
}
