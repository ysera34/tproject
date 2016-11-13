package com.tacademy.v02.chemi.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tacademy.v02.chemi.R;
import com.tacademy.v02.chemi.model.NavigationDrawerItem;

import java.util.ArrayList;

/**
 * Created by yoon on 2016. 11. 12..
 */

public class NavigationDrawerFragment extends Fragment {

    private static final String TAG = NavigationDrawerFragment.class.getSimpleName();

    private RecyclerView mNavigationRecyclerView;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private NavigationDrawerAdapter mNavigationDrawerAdapter;
    private View mNavigationContainerView;
    private static String[] titles;
    private NavigationDrawerFragmentListener drawerListener;

    public NavigationDrawerFragment() {
    }

    public void setDrawerListener(NavigationDrawerFragmentListener listener) {
        this.drawerListener = listener;
    }

    public static ArrayList<NavigationDrawerItem> getDrawerItems() {
        ArrayList<NavigationDrawerItem> navItems = new ArrayList<>();

        // preparing navigation drawer items
        for (int i = 0; i < titles.length; i++) {
            NavigationDrawerItem navItem = new NavigationDrawerItem();
            navItem.setTitle(titles[i]);
            navItems.add(navItem);
        }
        return navItems;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        titles = getActivity().getResources().getStringArray(R.array.nav_drawer_labels);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        mNavigationRecyclerView = (RecyclerView) view.findViewById(R.id.navigation_item_recycler_view);
        mNavigationRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mNavigationDrawerAdapter = new NavigationDrawerAdapter(getDrawerItems());
        mNavigationRecyclerView.setAdapter(mNavigationDrawerAdapter);


        mNavigationRecyclerView.addOnItemTouchListener(
                new RecyclerTouchListener(getActivity(), mNavigationRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                drawerListener.onDrawerItemSelected(view, position);
                mDrawerLayout.closeDrawer(mNavigationContainerView);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        return view;
    }

    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
        mNavigationContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mActionBarDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                toolbar.setAlpha(1 - slideOffset / 2);
            }
        };

        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mActionBarDrawerToggle.syncState();
            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationItemHolder>{

        private ArrayList<NavigationDrawerItem> mItems;

        public NavigationDrawerAdapter(ArrayList<NavigationDrawerItem> items) {
            mItems = items;
        }

        @Override
        public NavigationItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.navigation_drawer_item, parent, false);
            return new NavigationItemHolder(view);
        }

        @Override
        public void onBindViewHolder(NavigationItemHolder holder, int position) {
            NavigationDrawerItem item = mItems.get(position);
            holder.bindItem(item);
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }

        public void delete(int position) {
            mItems.remove(position);
            notifyItemRemoved(position);
        }
    }

    private class NavigationItemHolder extends RecyclerView.ViewHolder {

        private NavigationDrawerItem mNavigationDrawerItem;
        private TextView mTitleTextView;

        public NavigationItemHolder(View itemView) {
            super(itemView);
            mTitleTextView = (TextView) itemView.findViewById(R.id.navigation_item_title);
        }

        public void bindItem(NavigationDrawerItem navigationDrawerItem) {
            mNavigationDrawerItem = navigationDrawerItem;
            mTitleTextView.setText(mNavigationDrawerItem.getTitle());
        }
    }

    public static interface ClickListener {

        public void onClick(View view, int position);

        public void onLongClick(View view, int position);
    }

    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector mGestureDetector;
        private ClickListener mClickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView,
                                     final ClickListener clickListener) {
            mClickListener = clickListener;
            mGestureDetector = new GestureDetector(
                    context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && mClickListener != null && mGestureDetector.onTouchEvent(e)) {
                mClickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

    public interface NavigationDrawerFragmentListener {
        public void onDrawerItemSelected(View view, int position);
    }
}
