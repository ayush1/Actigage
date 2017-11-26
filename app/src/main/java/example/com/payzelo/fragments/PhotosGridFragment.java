package example.com.payzelo.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import adapter.GridAdapter;
import example.com.payzelo.R;
import utils.CommonUtil;

/**
 * Created by ayushgarg on 25/11/17.
 */

public class PhotosGridFragment extends Fragment {

    private GridView gridView;
    private GridAdapter gridAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grid, container, false);
        gridView = (GridView) view.findViewById(R.id.grid);

        return view;
    }

    public void updateAdapter() {
        gridAdapter = new GridAdapter(CommonUtil.getPhotoList(), getActivity());
        gridView.setAdapter(gridAdapter);
    }

}
