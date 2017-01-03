package org.uk.cyberbyte.armaworks;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    public SharedPreferences getSettings() {
        return getSharedPreferences("ArmaWorksSettings", MODE_PRIVATE);
    }
}
