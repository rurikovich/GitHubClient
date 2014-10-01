package com.orangapps.githubclient4lightsoft;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.orangapps.githubclient4lightsoft.data.User;
import com.orangapps.githubclient4lightsoft.data.UsersDataHolder;
import com.orangapps.githubclient4lightsoft.ui.StableArrayAdapter;
import com.orangapps.githubclient4lightsoft.ui.UserDetailsDialog;

import java.util.ArrayList;


public class MyActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, AbsListView.OnScrollListener, SwipeRefreshLayout.OnRefreshListener {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

//    private ProgressDialog progressDialog;

    private UsersDataHolder dataHolder;

    private int preLastListItem;
    private StableArrayAdapter adapter;
    private ListView listview;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private ArrayList<String> last5Users = new ArrayList<String>();

    private class FetchUsersAsyncTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mSwipeRefreshLayout.setRefreshing(false);
            mSwipeRefreshLayout.setColorScheme(android.R.color.darker_gray,
                    android.R.color.holo_blue_light,
                    android.R.color.secondary_text_light_nodisable,
                    android.R.color.holo_blue_dark);

            mSwipeRefreshLayout.setRefreshing(true);
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                dataHolder.fetchNewUsersNotAsync();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String str) {
            super.onPostExecute(str);
            mSwipeRefreshLayout.setRefreshing(false);
            adapter.addUsers(dataHolder.getUsers());
            adapter.updateUsersList();
            adapter.notifyDataSetChanged();
        }
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));


        dataHolder = new UsersDataHolder();
        try {
            dataHolder.init();
        } catch (Exception e) {
            e.printStackTrace();
        }

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.pull_to_request_layout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        // делаем повеселее
        listview = (ListView) findViewById(R.id.users_list);
        adapter = new StableArrayAdapter(this, dataHolder.getUsers());
        listview.setOnScrollListener(this);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                User user = dataHolder.getUsers().get(position);
                mNavigationDrawerFragment.addUserToLastList(user.getLogin());
                new UserDetailsDialog(MyActivity.this, user).show();
            }

        });


    }


    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }



    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the onImageDownloadedAction bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the onImageDownloadedAction bar.
            getMenuInflater().inflate(R.menu.my, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle onImageDownloadedAction bar item clicks here. The onImageDownloadedAction bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == 0) {
            adapter.updateUsersList();
            adapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        switch (view.getId()) {
            case R.id.users_list:
                final int lastItem = firstVisibleItem + visibleItemCount;
                if (lastItem == totalItemCount) {
                    if (preLastListItem != lastItem) { //to avoid multiple calls for last item

                        FetchUsersAsyncTask fetchUsersAsyncTask = new FetchUsersAsyncTask();
                        fetchUsersAsyncTask.execute();

                        preLastListItem = lastItem;
                    }
                }
        }
    }

    @Override
    public void onRefresh() {
        adapter.clear();
        dataHolder.clear();
        new FetchUsersAsyncTask() {
            @Override
            protected void onPreExecute() {
                mSwipeRefreshLayout.setRefreshing(false);
                mSwipeRefreshLayout.setColorScheme(android.R.color.holo_red_dark,
                        android.R.color.holo_orange_dark,
                        android.R.color.holo_purple,
                        android.R.color.holo_green_dark);
                mSwipeRefreshLayout.setRefreshing(true);
            }
        }.execute();

    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_my, container, false);
            return rootView;
        }

    }


}
