package xyz.a00000.k1subscreenshortcut.service;

import android.app.ActivityOptions;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.view.Display;

import androidx.annotation.Nullable;

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

    @Nullable
    private Display getSubScreenDisplay() {
        Display[] displays = mDisplayUtils.getAllDisplay();
        if (displays != null) {
            for (Display display : displays) {
                Display.Mode mode = display.getMode();
                int physicalWidth = mode.getPhysicalWidth();
                int physicalHeight = mode.getPhysicalHeight();
                if (Math.abs(physicalWidth - 126) < 10 && Math.abs(physicalHeight - 294) < 10) {
                    return display;
                }
            }
        }
        return null;
    }

    public boolean startSubScreenActivity() {
        if (mSubDisplay == null && (mSubDisplay = getSubScreenDisplay()) == null) {
            return false;
        }
        ActivityOptions options = ActivityOptions.makeBasic();
        options.setLaunchDisplayId(mSubDisplay.getDisplayId());
        Intent subActivity = new Intent(this, SubScreenActivity.class);
        subActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(subActivity, options.toBundle());
        return true;
    }

    public class LocalBinder extends Binder {
        public SubScreenLaunchService getService() {
            return SubScreenLaunchService.this;
        }
    }

}