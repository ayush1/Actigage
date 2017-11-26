package example.com.payzelo.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import adapter.ListViewAdapter;
import example.com.payzelo.R;
import utils.CommonUtil;

/**
 * Created by ayushgarg on 25/11/17.
 */

public class PhotosListFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter listAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.rec_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        return view;
    }

    public void updateAdapter() {
        listAdapter = new ListViewAdapter(CommonUtil.getPhotoList(), getActivity());
        recyclerView.setAdapter(listAdapter);
    }
}
