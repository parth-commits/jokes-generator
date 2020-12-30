package com.example.android.jokesgenerator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
//import androidx.loader.app.LoaderManager.LoaderCallbacks;

import android.annotation.SuppressLint;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<Joke> {

    public static final String LOG_TAG = MainActivity.class.getName();
    private String mUrl = "https://official-joke-api.appspot.com/random_joke";
    private TextView mEmptyStateTextView;
    private ImageView mNoNet;
    private SwipeRefreshLayout swipeRefreshLayout;
    private static final int MAIN_LOADER_ID = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        swipeRefreshLayout = findViewById(R.id.swiperefresh);
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);

        //ScrollView scrollView = (ScrollView) findViewById(R.id.mainscrollview);


        mNoNet = (ImageView) findViewById(R.id.nonet);
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();


            ScrollView scrollView = findViewById(R.id.mainscrollview);
            scrollView.setVisibility(View.VISIBLE);
            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(MAIN_LOADER_ID, null, this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            ScrollView scrollView = findViewById(R.id.mainscrollview);
            scrollView.setVisibility(View.GONE);
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);
            mNoNet.setVisibility(View.VISIBLE);
            mEmptyStateTextView.setText(R.string.no_internet_connection);
            // Update empty state with no connection error message
        }


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Intent intent = new Intent( MainActivity.this, redirect.class);
                // Send the intent to launch a new activity
                startActivity(intent);
                //your code on swipe refresh
                /*ConnectivityManager connMgr = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);

                // Get details on the currently active default data network
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                // If there is a network connection, fetch data
                if (networkInfo != null && networkInfo.isConnected()) {
                    // Get a reference to the LoaderManager, in order to interact with loaders.
                    mNoNet.setVisibility(View.GONE);
                    System.out.println("htis makes it here");
                    LoaderManager loaderManager1 = getLoaderManager();

                    // Initialize the loader. Pass in the int ID constant defined above and pass in null for
                    // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
                    // because this activity implements the LoaderCallbacks interface).
                    loaderManager1.initLoader(MAIN_LOADER_ID, null,  MainActivity.this);
                } else {
                    // Otherwise, display error
                    // First, hide loading indicator so error message will be visible
                    //adapter.clear();
                    View loadingIndicator = findViewById(R.id.loading_indicator);
                    loadingIndicator.setVisibility(View.GONE);
                    mNoNet.setVisibility(View.VISIBLE);
                    mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
                    mEmptyStateTextView.setText(R.string.no_internet_connection);
                    // Update empty state with no connection error message
                }
                swipeRefreshLayout.setRefreshing(false);*/
            }
        });

    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_favorite:
                // User chose the "Settings" item, show the app settings UI...
                Intent countryIntent = new Intent(MainActivity.this, about.class);
                // Send the intent to launch a new activity
                startActivity(countryIntent);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // R.menu.mymenu is a reference to an xml file named mymenu.xml which should be inside your res/menu directory.
        // If you don't have res/menu, just create a directory named "menu" inside res
        getMenuInflater().inflate(R.menu.creditbutton, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @NonNull
    @Override
    public Loader<Joke> onCreateLoader(int id, @Nullable Bundle args) {
        return new JokeLoader(this, mUrl);
    }


    @Override
    public void onLoadFinished(android.content.Loader<Joke> loader, Joke joke) {
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        if (joke.getCategory().equals("NoJokeAvailable")){
            // Set empty state text to display "No earthquakes found."
            mEmptyStateTextView.setText(R.string.no_joke);
        }

        TextView categorytitle = findViewById(R.id.categorytitle);
        categorytitle.setText("CATEGORY:");
        //find the views, and load the stuff
        TextView category = findViewById(R.id.category);

        TextView setup = findViewById(R.id.setup);

        TextView delivery = findViewById(R.id.delivery);

        category.setText(joke.getCategory());
        setup.setText(joke.getSetup());
        delivery.setText(joke.getDelivery());

    }

    @Override
    public void onLoaderReset(android.content.Loader<Joke> loader) {
        //loader
    }

}