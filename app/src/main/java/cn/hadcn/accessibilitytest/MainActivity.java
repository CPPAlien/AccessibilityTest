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
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    final String TAG = "AccessibilityTest:";
    TextView tvGrantStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LocalBroadcastManager.getInstance(this).registerReceiver((receiver),
                new IntentFilter(MyAccessibilityService.ACCESSIBILITY_INFO)
        );
        findViewById(R.id.button_set).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                startActivity(intent);
            }
        });
        tvGrantStatus = (TextView)findViewById(R.id.grant_status);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e(TAG, "broadcast msg received");
            Boolean isGranted = intent.getBooleanExtra(MyAccessibilityService.IS_GRANTED, false);
            if ( isGranted ) {
                tvGrantStatus.setText("Accessibility is working");
                tvGrantStatus.setTextColor(Color.GREEN);
            } else {
                tvGrantStatus.setText("Accessibility is denied");
                tvGrantStatus.setTextColor(Color.RED);
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }
}
