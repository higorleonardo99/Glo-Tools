package bo.hlva.glotools.ui.fragments;

import android.os.Bundle;
import androidx.preference.PreferenceFragmentCompat;
import bo.hlva.glotools.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle bundle, String rootKey) {
        setPreferencesFromResource(R.xml.preferences_main,rootKey);
    }

}
