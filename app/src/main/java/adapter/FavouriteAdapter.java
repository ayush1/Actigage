package adapter;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import db.Favourite;
import example.com.payzelo.R;
import example.com.payzelo.fragments.FavouriteFragment;
import utils.AppConstants;

/**
 * Created by ayushgarg on 26/11/17.
 */

public class FavouriteAdapter extends BaseAdapter {

    List<Favourite> favouriteList = new ArrayList();
    Context context;
    FavouriteFragment favouriteFragment;

    public FavouriteAdapter(List<Favourite> favouriteList, Context context, FavouriteFragment favouriteFragment) {
        this.favouriteList = favouriteList;
        this.context = context;
        this.favouriteFragment = favouriteFragment;
    }

    @Override
    public int getCount() {
        return favouriteList.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view == null){
            view  = LayoutInflater.from(context).inflate(R.layout.row_layout, null);
        }

        ImageView imageView = (ImageView) view.findViewById(R.id.iv);
        imageView.setTag(Integer.valueOf(i));

        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete");
                builder.setMessage("Are you sure, you want to delete?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        favouriteFragment.deleteFromDb((Integer) view.getTag());
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                builder.show();
                return true;
            }
        });

        ContextWrapper cw = new ContextWrapper(context);
        final File directory = cw.getDir(AppConstants.DIR_NAME, Context.MODE_PRIVATE);

        Picasso.with(context)
                .load(new File(directory, favouriteList.get(i).getFavUrl()))
                .into(imageView);

        return view;
    }
}
