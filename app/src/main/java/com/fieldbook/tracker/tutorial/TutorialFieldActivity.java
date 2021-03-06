package com.fieldbook.tracker.tutorial;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.fieldbook.tracker.ConfigActivity;
import com.fieldbook.tracker.R;


public class TutorialFieldActivity extends Activity {
    public static Activity thisActivity;

    private int screen;

    private final int max = 4;

    @Override
    public void onDestroy() {
        ConfigActivity.helpActive = false;
        super.onDestroy();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ConfigActivity.helpActive = true;

        SharedPreferences ep = getSharedPreferences("Settings", 0);

        thisActivity = this;

        // Makes the screen a system alert, so it can "float" above other screens        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        getWindow().setGravity(Gravity.BOTTOM);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        setContentView(R.layout.activity_tutorial);

        Button close = findViewById(R.id.close);
        Button prev = findViewById(R.id.prev);
        Button next = findViewById(R.id.next);

        final TextView header = findViewById(R.id.header);
        final TextView content = findViewById(R.id.field_count);

        screen = 1;

        // Load help strings        
        final String array[] = new String[max];
        array[0] = getString(R.string.fhelp1);
        array[1] = getString(R.string.fhelp2);
        array[2] = getString(R.string.fhelp3);
        array[3] = getString(R.string.fhelp4);

        header.setText(getString(R.string.tutorial) + " " + screen + "/" + max);
        content.setText(array[screen - 1]);

        // move one step back in the tutorial        
        prev.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {

                screen -= 1;

                if (screen < 1)
                    screen = 1;

                header.setText(getString(R.string.tutorial) + " " + screen + "/" + max);
                content.setText(array[screen - 1]);
            }
        });

        // move one step forward in the tutorial
        // In step 3, open the import dialog for the user as the user can't access 
        // the action bar. The initial delay has been removed - this is to prevent
        // the user from opening other dialogs, which then hide the import dialog
        // when it opens
        next.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {

                screen += 1;

                if (screen > max)
                    screen = max;

                header.setText(getString(R.string.tutorial) + " " + screen + "/" + max);
                content.setText(array[screen - 1]);
            }
        });

        // close screen
        // help active is to indicate tips/hints is no longer open
        // user is now able to open tutorial        
        close.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                ConfigActivity.helpActive = false;
                finish();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

}
