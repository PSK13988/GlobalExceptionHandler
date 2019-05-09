package org.sterl.android.globalexceptionhandler;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends BaseActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listView = (ListView) findViewById(R.id.listView);

        findViewById(R.id.cmdAuthError).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //throw new RuntimeException(); // we just fail with our custom exception ...
                int a = 10 / 0;
            }
        });
        findViewById(R.id.cmdRandomError).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                throw new IndexOutOfBoundsException();
            }
        });
        TextView textView = (TextView) findViewById(R.id.textView2);
        textView.setText(getText(R.string.str_please_find_mylog_log_file_in_sdcard_to_view_the_crash_logs));
        askPermission();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1,
                LoggingExceptionHandler.readExceptions(this));
        listView.setAdapter(adapter);
    }

    private void askPermission() {
        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        Permissions.check(this, permissions, null, null, new PermissionHandler() {
            @Override
            public void onGranted() {

            }

            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                super.onDenied(context, deniedPermissions);
                Toast.makeText(MainActivity.this, "File write permission are not allowed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}