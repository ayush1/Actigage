package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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

public class GridAdapter extends BaseAdapter{

    private AppDatabase appDatabase;
    ArrayList<String> arrayList = new ArrayList();
    Context context;

    int numberOfTaps = 0;
    long lastTapTimeMs = 0;
    long touchDownMs = 0;

    public GridAdapter(ArrayList arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;

        appDatabase = AppDatabase.getAppDatabaseInstance(context);
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        if(view == null){
            view  = LayoutInflater.from(context).inflate(R.layout.row_layout, null);
        }

        ImageView imageView = (ImageView) view.findViewById(R.id.iv);
        imageView.setOnTouchListener(new View.OnTouchListener() {

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
                            String hashUrl = CommonUtil.generateHash(arrayList.get(i));

                            Picasso.with(context)
                                    .load(arrayList.get(i))
                                    .into(ImageStorage.getTarget(context, AppConstants.DIR_NAME, hashUrl));
                            DatabaseInitialiser.populateAsync(appDatabase, hashUrl);
                        }
                }
                return true;
            }
        });

        Picasso.with(context)
                .load(arrayList.get(i))
                .into(imageView);

        return view;
    }
}
