package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

import db.AppDatabase;
import example.com.payzelo.R;
import utils.AppConstants;
import utils.CommonUtil;
import utils.DatabaseInitialiser;
import utils.ImageStorage;
import utils.ViewUtils;

/**
 * Created by ayushgarg on 25/11/17.
 */

public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.ListViewRecycler> {

    private AppDatabase appDatabase;
    ArrayList<String> arrayList = new ArrayList();
    Context context;

    int numberOfTaps = 0;
    long lastTapTimeMs = 0;
    long touchDownMs = 0;

    public ListViewAdapter(ArrayList arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;

        appDatabase = AppDatabase.getAppDatabaseInstance(context);
    }

    @Override
    public ListViewRecycler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_list_layout, parent, false);
        return new ListViewRecycler(view);
    }

    @Override
    public void onBindViewHolder(ListViewRecycler holder, final int position) {

        // Double tap on the items logic
        holder.iv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        touchDownMs = System.currentTimeMillis();
                        break;
                    case MotionEvent.ACTION_UP:
                        if ((System.currentTimeMillis() - touchDownMs) > ViewConfiguration.getTapTimeout()) {
                            numberOfTaps = 0;
                            lastTapTimeMs = 0;
                            break;
                        }

                        if (numberOfTaps > 0 &&
                                (System.currentTimeMillis() - lastTapTimeMs) < ViewConfiguration.getDoubleTapTimeout()) {
                            numberOfTaps += 1;
                        } else {
                            numberOfTaps = 1;
                        }

                        lastTapTimeMs = System.currentTimeMillis();

                        if (numberOfTaps == 2) {
                            ViewUtils.showToastMessage(context, "Added to favourite");
                            String hashUrl = CommonUtil.generateHash(arrayList.get(position));

                            Picasso.with(context)
                                    .load(arrayList.get(position))
                                    .into(ImageStorage.getTarget(context, AppConstants.DIR_NAME, hashUrl));
                            DatabaseInitialiser.populateAsync(appDatabase, hashUrl);
                        }
                }
                return true;
            }
        });

        Picasso.with(context)
                .load(arrayList.get(position))
                .into(holder.iv);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ListViewRecycler extends RecyclerView.ViewHolder{
        ImageView iv;

        public ListViewRecycler(View itemView) {
            super(itemView);

            iv = (ImageView) itemView.findViewById(R.id.iv_list);
        }
    }
}
