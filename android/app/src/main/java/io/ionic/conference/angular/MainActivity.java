package io.ionic.conference.angular;

import android.provider.Settings;
import androidx.appcompat.app.AlertDialog;
import com.getcapacitor.BridgeActivity;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends BridgeActivity {

    @Override
    protected void onResume() {
        super.onResume();
        warnIfThirdPartyImeActive();
    }

    private void warnIfThirdPartyImeActive() {
        String currentIme = Settings.Secure.getString(getContentResolver(), Settings.Secure.DEFAULT_INPUT_METHOD);
        if (currentIme == null) {
            return;
        }
        Set<String> systemPackages = new HashSet<>(Arrays.asList(
            "com.android.inputmethod.latin",
            "com.google.android.inputmethod.latin",
            "com.samsung.android.honeyboard"
        ));
        int slash = currentIme.indexOf('/');
        String activePackage = slash >= 0 ? currentIme.substring(0, slash) : currentIme;
        if (!systemPackages.contains(activePackage)) {
            new AlertDialog.Builder(this)
                .setTitle("Security Warning")
                .setMessage("A third-party keyboard is active. Sensitive data entry may be monitored.")
                .setPositiveButton("Continue", (dialog, which) -> dialog.dismiss())
                .show();
        }
    }
}
