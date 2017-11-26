package utils;

/**
 * Created by ayushgarg on 24/11/17.
 */

public class ConstUrl {

    public static final String BASE_URL = "https://api.flickr.com/services/rest/";
    public static final String format = "&format=json&nojsoncallback=1";
    public static final String FLICKR_URL = BASE_URL + "?method=flickr.photos.search&api_key=" + AppConstants.API_KEY;

}
