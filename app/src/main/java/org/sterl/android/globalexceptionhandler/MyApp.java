package org.sterl.android.globalexceptionhandler;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

/**
 * We register our Exception handler for the Android Application, as so we don't have to do
 * this in each activity.
 */
public class MyApp extends Application {
    private static final String TAG = "MyApp";

    @Override
    public void onCreate() {
        super.onCreate();
        //new LoggingExceptionHandler(this);
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread paramThread, Throwable paramThrowable) {
                Log.d(TAG, "uncaughtException: App Forced Closed");
                handleUncaughtException(paramThread, paramThrowable);
                /*final ProgressDialog dialog = new ProgressDialog(getApplicationContext());
                dialog.setMessage("Please wait");
                dialog.show();
                dialog.setCancelable(false);
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        android.os.Process.killProcess(android.os.Process.myPid());
                    }
                }, 3000);*/

            }
        });
    }

    public void handleUncaughtException(Thread thread, Throwable e) {
        e.printStackTrace(); // not all Android versions will print the stack trace automatically

        Intent registerActivity = new Intent(getApplicationContext(), AuthActivity.class);
//        registerActivity.putExtra(EXTRA_MY_EXCEPTION_HANDLER, MyExceptionHandler.class.getName());
        registerActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        registerActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);

        getApplicationContext().startActivity(registerActivity);

        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
}
