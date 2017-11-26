package example.com.payzelo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

import example.com.payzelo.fragments.FavouriteFragment;
import example.com.payzelo.fragments.TabFragment;
import model.Photo;
import model.Photos;
import utils.CommonUtil;
import utils.ConstUrl;
import utils.NetworkUtils;

/**
 * Created by ayushgarg on 24/11/17.
 */

public class MainActivity extends AppCompatActivity{

    Context context;
    Gson gson;
    TabFragment tabFragment;
    FavouriteFragment favouriteFragment;
    BottomNavigationView bottomNavigationView;

    ArrayList<String> photoUrls = new ArrayList<>();

    FragmentManager fragmentManager;
    String url;
    private FragmentTransaction fragmentTransaction;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        gson = new Gson();

        tabFragment = new TabFragment();
        favouriteFragment = new FavouriteFragment();

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container, tabFragment).commit();

        progressDialog = new ProgressDialog(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem search_item = menu.findItem(R.id.search);

        SearchView searchView = (SearchView) search_item.getActionView();
        searchView.setFocusable(false);
        searchView.setQueryHint("Search");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String str) {
                if(NetworkUtils.isNetworkAvailable(context)){
                    GetPhotosFromUrl getPhotosFromUrl = new GetPhotosFromUrl((Activity) context);
                    url = ConstUrl.FLICKR_URL + "&tags=" + str + ConstUrl.format;
                    getPhotosFromUrl.execute(url);
                }else{
                    Toast.makeText(MainActivity.this, "No internet connection available", Toast.LENGTH_SHORT).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }

    private boolean favClicked;
    private boolean tabClicked;
    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            switch(item.getItemId()){
                case R.id.navigation_search:
                    if(!tabClicked) {
                        fragmentManager.popBackStack();
                        fragmentManager.beginTransaction().remove(favouriteFragment);
                        fragmentManager.beginTransaction().show(tabFragment);
                        item.setIcon(R.drawable.search_selected);
                        tabFragment.callAdapters();
                        favClicked = false;
                        tabClicked = true;
                    }
                    return true;
                case R.id.navigation_favourite:
                    if (!favClicked) {
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.hide(tabFragment);
                        fragmentTransaction.add(R.id.container, favouriteFragment);
                        fragmentTransaction.addToBackStack("TabFragment").commit();
                        favClicked = true;
                        tabClicked = false;
                    }
                    return true;
            }
            return false;
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public class GetPhotosFromUrl extends AsyncTask<String, Void, String>{

        private String resp = null;
        private Activity activity;

        public GetPhotosFromUrl(Activity activity) {
            this.activity = activity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            resp = NetworkUtils.getResponse(strings[0]);
            return resp;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(progressDialog.isShowing()){
                progressDialog.dismiss();
                activity.getCurrentFocus().clearFocus();
            }
            try {
                JSONObject jsonObject = new JSONObject(s);
                Photos photos = gson.fromJson(jsonObject.getJSONObject("photos").toString(), Photos.class);
                ArrayList list = photos.getPhoto();

                photoUrls.clear();

                for(int i = 0; i < list.size(); i++){
                    Photo photo = (Photo) list.get(i);
                    photoUrls.add(CommonUtil.generatePhotoURL(photo.getFarm(), photo.getId(), photo.getSecret(),
                                                photo.getServer()));
                }
                CommonUtil.getPhotoList().clear();
                CommonUtil.setPhotoList(photoUrls);

                tabFragment.callAdapters();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
