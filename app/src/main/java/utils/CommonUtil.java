package utils;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by ayushgarg on 24/11/17.
 */

public class CommonUtil {

    private static ArrayList photoList = new ArrayList();
    private static ArrayList favouriteList = new ArrayList();


    public static String generatePhotoURL(String farm, String id, String secret, String server){
        String  url = "https://farm" + farm + ".staticflickr.com/" + server + "/" + id + "_" + secret + "_m.jpg";
        return url;
    }

    public static ArrayList getPhotoList() {
        return photoList;
    }

    public static void setPhotoList(ArrayList list) {
        for(int i = 0; i < list.size(); i++){
            photoList.add(list.get(i));
        }
    }

    public static ArrayList getFavouriteList() {
        return favouriteList;
    }

    /**
     * Here we generated unique string for each photo url to store it in the Db
     * @param str
     * @return
     */
    public static String generateHash(String str){
        return UUID.nameUUIDFromBytes(str.getBytes()).toString();
    }

}
