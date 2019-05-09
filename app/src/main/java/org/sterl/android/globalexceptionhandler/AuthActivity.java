package org.sterl.android.globalexceptionhandler;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;

import java.io.File;
import java.io.IOException;

public class AuthActivity extends BaseActivity {
    private static final String TAG = "AuthActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        File filename = new File(Environment.getExternalStorageDirectory() + "/mylog.log");
        try {
            filename.createNewFile();
            Log.d(TAG, "run: file created");
        } catch (IOException e) {
            e.printStackTrace();
        }
        String cmd = "logcat -d -f" + filename.getAbsolutePath();
        try {
            Runtime.getRuntime().exec(cmd);
            Log.d(TAG, "onCreate: logcat written");
        } catch (IOException e) {
            e.printStackTrace();
        }

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
