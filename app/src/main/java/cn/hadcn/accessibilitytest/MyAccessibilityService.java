package cn.hadcn.accessibilitytest;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;


/**
 * MyAccessibilityService
 * Created by 90Chris on 2016/2/27.
 */
public class MyAccessibilityService extends AccessibilityService {
    final String TAG = "AccessibilityTest:";
    LocalBroadcastManager broadcaster;

    @Override
    public void onCreate() {
        super.onCreate();
        broadcaster = LocalBroadcastManager.getInstance(this);
    }

    static final public String ACCESSIBILITY_INFO = "cn.hadcn.accessibilitytest.ACCESSIBILITY_INFO";
    static final public String IS_GRANTED = "cn.hadcn.accessibilitytest.IS_GRANTED";

    /**
     * broadcast Accessibility permission status,
     * ui component could get the status via receiver
     * @param isGranted is the Accessibility permission granted to the app
     */
    public void broadCastMsg(Boolean isGranted) {
        Intent intent = new Intent(ACCESSIBILITY_INFO);
        intent.putExtra(IS_GRANTED, isGranted);
        broadcaster.sendBroadcast(intent);
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        Log.e(TAG, "onServiceConnected");
        broadCastMsg(true);
        startActivity(new Intent(this, CheckAccessibilityActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e(TAG, "onUnbind");
        broadCastMsg(false);
        startActivity(new Intent(this, CheckAccessibilityActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        return super.onUnbind(intent);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.e(TAG, "onAccessibilityEvent");
    }

    @Override
    public void onInterrupt() {
        Log.e(TAG, "onInterrupt");
    }


}
