package com.example.android.jokesgenerator;

import android.app.Activity;
import android.content.Intent;
import android.graphics.text.LineBreaker;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class redirect extends Activity {


    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        Intent intent = new Intent(redirect.this, MainActivity. class);
        // Send the intent to launch a new activity
        startActivity(intent);
    }
}
