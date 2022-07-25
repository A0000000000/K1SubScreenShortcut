package xyz.a00000.k1subscreenshortcut.utils;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.annotation.Nullable;

import java.lang.reflect.Method;

@SuppressLint("PrivateApi")
public class SystemPropertiesUtils {

    @Nullable
    public static String getSystemProperties(Context context, String key) {
        try {
            ClassLoader classLoader = context.getClassLoader();
            Class<?> systemPropertiesClass = classLoader.loadClass("android.os.SystemProperties");
            Method getProperties = systemPropertiesClass.getMethod("get", String.class);
            return (String) getProperties.invoke(null, key);
        } catch (Exception e) {
            return null;
        }
    }

}
