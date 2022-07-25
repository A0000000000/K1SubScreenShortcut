package xyz.a00000.k1subscreenshortcut;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import xyz.a00000.k1subscreenshortcut.service.SubScreenLaunchService;
import xyz.a00000.k1subscreenshortcut.utils.DisplayUtils;
import xyz.a00000.k1subscreenshortcut.utils.SystemPropertiesUtils;


public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getName();

    private DisplayUtils mDisplayUtils;
    private SubScreenLaunchService mSubScreenLaunchService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDisplayUtils = new DisplayUtils(this);
        String deviceName = SystemPropertiesUtils.getSystemProperties(this, "ro.product.name");
        if (!TextUtils.equals("star", deviceName) && !mDisplayUtils.checkDisplayCount()) {
            Toast.makeText(this, "该设备不是小米11 Ultra, 请使用小米11 Ultra运行此软件.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        bindService(new Intent(this, SubScreenLaunchService.class), new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                if (service instanceof SubScreenLaunchService.LocalBinder) {
                    mSubScreenLaunchService = ((SubScreenLaunchService.LocalBinder) service).getService();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, BIND_AUTO_CREATE);

        findViewById(R.id.btn).setOnClickListener(view -> {
            if (mSubScreenLaunchService != null) {
                mSubScreenLaunchService.startSubScreenActivity();
            }
        });

    }





}