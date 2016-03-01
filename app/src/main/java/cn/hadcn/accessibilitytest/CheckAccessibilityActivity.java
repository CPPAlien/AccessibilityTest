package cn.hadcn.accessibilitytest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.provider.Settings;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheckAccessibilityActivity extends AppCompatActivity {
    final String TAG = "AccessibilityTest:";
    TextView tvGrantStatus;
    Button btnSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvGrantStatus = (TextView)findViewById(R.id.grant_status);
        btnSetting = (Button)findViewById(R.id.button_set);

        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                startActivity(intent);
            }
        });

        if ( AccessibilityStatus.newInstance(this).get() ) {
            showGranted();
        } else {
            showDenied();
        }

        LocalBroadcastManager.getInstance(this).registerReceiver((receiver),
                new IntentFilter(MyAccessibilityService.ACCESSIBILITY_INFO)
        );
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e(TAG, "broadcast msg received");
            Boolean isGranted = intent.getBooleanExtra(MyAccessibilityService.IS_GRANTED, false);
            if ( isGranted ) {
                showGranted();
            } else {
                showDenied();
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    private void showGranted() {
        tvGrantStatus.setText(getString(R.string.permission_granted));
        tvGrantStatus.setTextColor(Color.GREEN);
        btnSetting.setVisibility(View.GONE);
        AccessibilityStatus.newInstance(this).set(true);
    }

    private void showDenied(){
        tvGrantStatus.setText(getString(R.string.permission_denied));
        tvGrantStatus.setTextColor(Color.RED);
        btnSetting.setVisibility(View.VISIBLE);
        AccessibilityStatus.newInstance(this).set(false);
    }
}
