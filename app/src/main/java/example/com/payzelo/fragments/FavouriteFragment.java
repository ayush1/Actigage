package example.com.payzelo.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import adapter.FavouriteAdapter;
import db.AppDatabase;
import db.Favourite;
import example.com.payzelo.MainActivity;
import example.com.payzelo.R;

/**
 * Created by ayushgarg on 26/11/17.
 */

public class FavouriteFragment extends Fragment implements View.OnKeyListener {

    private GridView gridView;
    private FavouriteAdapter favouriteAdapter;
    private TextView tvNoItems;

    AppDatabase appDatabase;
    private static List<Favourite> favouritesList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appDatabase = AppDatabase.getAppDatabaseInstance(getActivity());

        setHasOptionsMenu(true);
        getActivity().setTitle("Favourites");

        FetchDataFromDb fetchDataFromDb = new FetchDataFromDb(this, appDatabase);
        fetchDataFromDb.execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grid, container, false);
        gridView = (GridView) view.findViewById(R.id.grid);
        tvNoItems = (TextView) view.findViewById(R.id.tv_no_items);

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        String favouriteTitle;
        favouriteTitle = getString(R.string.favourite);
        MainActivity action = (MainActivity) getActivity();
        action.getSupportActionBar().setTitle(favouriteTitle);

        if(getView() == null){
            return;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        String searchTitle;
        searchTitle = getString(R.string.search_hint);
        MainActivity action = ((MainActivity)getActivity());
        action.getSupportActionBar().setTitle(searchTitle);

    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.search).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }

    /**
     * It sets the favourite items list which we will get from asynctask
     * @param favourites
     */
    public void setList(List<Favourite> favourites){
        if(favourites.size() == 0){
            tvNoItems.setVisibility(View.VISIBLE);
        }else {
            favouriteAdapter = new FavouriteAdapter(favourites, getActivity(), FavouriteFragment.this);
            gridView.setAdapter(favouriteAdapter);
        }
    }

    /**
     * update the favourite list after delete
     * @param favourites
     */
    public void updateList(List<Favourite> favourites){
        favouriteAdapter.notifyDataSetChanged();
    }

    /**
     * delete the selected item from DB
     * @param position
     */
    public void deleteFromDb(int position){
        DeleteFavouriteFromDb deleteFavouriteFromDb = new DeleteFavouriteFromDb(this, appDatabase, position);
        deleteFavouriteFromDb.execute();
    }

    @Override
    public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
        if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                return true;
            }
        }
        return false;
    }

    /**
     * This Async task will fetch all the favourite items from DB
     */
    private static class FetchDataFromDb extends AsyncTask<Void, Void, List<Favourite>> {
        private AppDatabase database;
        FavouriteFragment fragment;

        public FetchDataFromDb(FavouriteFragment favouriteFragment, AppDatabase database) {
            this.database = database;
            this.fragment = favouriteFragment;
        }

        @Override
        protected List<Favourite> doInBackground(Void... voids) {
            return database.favouriteDao().getAll();
        }

        @Override
        protected void onPostExecute(List<Favourite> favourites) {
            super.onPostExecute(favourites);
            fragment.setList(favourites);
            favouritesList = favourites;
        }
    }

    /**
     * This Async task will delete the favourite items from DB
     */
    private class DeleteFavouriteFromDb extends AsyncTask<Void, Void, Void> {
        private AppDatabase database;
        private FavouriteFragment fragment;
        private int position;

        public DeleteFavouriteFromDb(FavouriteFragment favouriteFragment, AppDatabase database, int position) {
            this.database = database;
            this.fragment = favouriteFragment;
            this.position = position;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            database.favouriteDao().delete(favouritesList.get(position));
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    favouritesList.clear();
                    favouritesList.addAll(database.favouriteDao().getAll());
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            fragment.updateList(favouritesList);
                        }
                    });
                }
            }).start();
        }
    }
}
