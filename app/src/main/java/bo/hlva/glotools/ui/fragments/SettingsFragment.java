package bo.hlva.glotools.ui.fragments;

import bo.hlva.glotools.R;
import androidx.preference.PreferenceFragment;
import android.os.Bundle;
import androidx.preference.PreferenceFragmentCompat;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle bundle, String rootKey) {
        setPreferencesFromResource(R.xml.preferences_main,rootKey);
    }

}
