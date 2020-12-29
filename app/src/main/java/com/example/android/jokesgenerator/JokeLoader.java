package com.example.android.jokesgenerator;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
//import androidx.loader.content.AsyncTaskLoader;
import android.content.AsyncTaskLoader;

public class JokeLoader extends AsyncTaskLoader<Joke> {

    //query url
    private String mUrl;

    public JokeLoader(@NonNull Context context, String mUrl) {
        super(context);
        this.mUrl = mUrl;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public Joke loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        return QueryUtils.fetchJokeData(mUrl);
    }
}
