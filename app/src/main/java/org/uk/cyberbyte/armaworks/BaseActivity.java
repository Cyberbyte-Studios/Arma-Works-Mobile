package org.uk.cyberbyte.armaworks;

import android.content.SharedPreferences;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    public SharedPreferences getSettings() {
        return getSharedPreferences("ArmaWorksSettings", MODE_PRIVATE);
    }
}
