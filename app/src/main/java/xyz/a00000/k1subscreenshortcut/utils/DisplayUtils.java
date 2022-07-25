package xyz.a00000.k1subscreenshortcut.utils;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.view.Display;

public class DisplayUtils {

    private Context mContext;
    private DisplayManager mDisplayManager;

    public DisplayUtils(Context context) {
        mContext = context;
        mDisplayManager = context.getSystemService(DisplayManager.class);
    }

    public boolean checkDisplayCount() {
        Display[] displays = mDisplayManager.getDisplays();
        return displays.length >= 2;
    }

    public Display[] getAllDisplay() {
        return mDisplayManager.getDisplays();
    }



}
