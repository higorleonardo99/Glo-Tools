package bo.hlva.glotools.ui.activities;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import bo.hlva.glotools.ui.fragments.SettingsFragment;

public class SettingsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        getSupportFragmentManager().
        beginTransaction().replace(android.R.id.content,new SettingsFragment(),"settings").
        commit();
    }
    
}
