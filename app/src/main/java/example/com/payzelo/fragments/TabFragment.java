package example.com.payzelo.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.*;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import example.com.payzelo.R;

/**
 * Created by ayushgarg on 24/11/17.
 */

public class TabFragment extends Fragment {

    private static TabLayout tabLayout;
    private static ViewPager viewPager;

    PhotosGridFragment gridFragment;
    PhotosListFragment listFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tabs, null);

        tabLayout = (TabLayout) view.findViewById(R.id.tab);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);

        viewPager.setAdapter(new FragmentAdapter(getChildFragmentManager(), 2));
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

    public void callAdapters() {
        gridFragment.updateAdapter();
        listFragment.updateAdapter();
    }

    class FragmentAdapter extends FragmentPagerAdapter{

        int tabCount;

        public FragmentAdapter(FragmentManager fm, int tabCount) {
            super(fm);
            this.tabCount = tabCount;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    gridFragment = new PhotosGridFragment();
                    return gridFragment;
                case 1:
                    listFragment = new PhotosListFragment();
                    return listFragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            return tabCount;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch(position){
                case 0:
                    return "Grid";
                case 1:
                    return "List";
            }
            return null;
        }
    }
}
