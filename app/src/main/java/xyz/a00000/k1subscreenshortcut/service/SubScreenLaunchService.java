package xyz.a00000.k1subscreenshortcut.service;

import android.app.ActivityOptions;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.view.Display;

import xyz.a00000.k1subscreenshortcut.activity.SubScreenActivity;
import xyz.a00000.k1subscreenshortcut.utils.DisplayUtils;

public class SubScreenLaunchService extends Service {

    public static final String TAG = SubScreenLaunchService.class.getName();
    private LocalBinder mBinder = new LocalBinder();

    private DisplayUtils mDisplayUtils;
    private Display mSubDisplay;

    @Override
    public void onCreate() {
        super.onCreate();
        mDisplayUtils = new DisplayUtils(this);
        mSubDisplay = getSubScreenDisplay();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private Display getSubScreenDisplay() {
        Display[] displays = mDisplayUtils.getAllDisplay();
        return displays[1];
    }

    public void startSubScreenActivity() {
        ActivityOptions options = ActivityOptions.makeBasic();
        options.setLaunchDisplayId(mSubDisplay.getDisplayId());
        Intent subActivity = new Intent(this, SubScreenActivity.class);
        subActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(subActivity, options.toBundle());
    }

    public class LocalBinder extends Binder {
        public SubScreenLaunchService getService() {
            return SubScreenLaunchService.this;
        }
    }

}