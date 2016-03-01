package cn.hadcn.accessibilitytest;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * for saving status of accessibility permission granted
 * default is false, means the app do not have the permission
 * true, the app get the permission
 * Created by 90Chris on 2016/3/1.
 */
public class AccessibilityStatus {
    private static final String PREF_NAME = "accessibility_pref";
    private static final String ITEM_STATUS = "status";
    private SharedPreferences mPref;
    private static AccessibilityStatus accessibilityStatus;

    public static AccessibilityStatus newInstance(Context context) {
        if ( accessibilityStatus == null ) {
            accessibilityStatus = new AccessibilityStatus(context);
        }
        return accessibilityStatus;
    }

    private AccessibilityStatus(Context context) {
        mPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void set(boolean status) {
        mPref.edit().putBoolean(ITEM_STATUS, status);
        mPref.edit().apply();
    }

    public boolean get() {
        return mPref.getBoolean(ITEM_STATUS, false);
    }
}
