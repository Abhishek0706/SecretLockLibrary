package com.example.secretlock;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.media.AudioManager;
import android.provider.Settings;

import com.example.secretlock.adapters.ItemAdapter;
import com.example.secretlock.models.Item;

import java.util.ArrayList;
import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;

public class SecretLock {

    /// Get settings value that is stored in shared preferences
    private HashMap<String, Boolean> getPreferenceValues(Context context) {

        SharedPreferences pref = context.getSharedPreferences("system_settings", MODE_PRIVATE);
        boolean p_wifi = pref.getBoolean("wifi_status", false);
        boolean p_bluetooth = pref.getBoolean("bluetooth_status", false);
        boolean p_airplane = pref.getBoolean("airplanemode_status", false);
        boolean p_ring = pref.getBoolean("ring_status", true);
        boolean p_gps = pref.getBoolean("gps_status", false);
        boolean p_rotate = pref.getBoolean("rotate_status", false);
//        boolean p_vibrate = pref.getBoolean("vibrate_status", false);

        HashMap<String, Boolean> _map = new HashMap<String, Boolean>();
        _map.put("wifi_status", p_wifi);
        _map.put("bluetooth_status", p_bluetooth);
        _map.put("airplanemode_status", p_airplane);
        _map.put("ring_status", p_ring);
        _map.put("gps_status", p_gps);
        _map.put("rotate_status", p_rotate);
//        _map.put("vibrate_status", p_vibrate);

        return _map;

    }

    /// Get current value of system settings
    private HashMap<String, Boolean> getSystemValues(Context context) {

        boolean bluetooth_status = false;/// bluetooth is on/off
        boolean airplanemode_status = false;///airplane mode on/off
        boolean ring_status = false;///ring profile on/off (off when silent)
        boolean wifi_status = false;///wifi is on/off
        boolean gps_status = false;///gps is on/off
        boolean rotate_status = false;///auto rotate is on/off
//        boolean vibrate_status = false;///vibration is on/off

        /// wifi status
        wifi_status = getBoolean(Integer.parseInt(Settings.System.getString(context.getContentResolver(), Settings.System.WIFI_ON)));

        /// bluetooth status
        bluetooth_status = getBoolean(Integer.parseInt(Settings.System.getString(context.getContentResolver(), Settings.System.BLUETOOTH_ON)));

        /// airplane mode status
        airplanemode_status = getBoolean(Integer.parseInt(Settings.System.getString(context.getContentResolver(), Settings.System.AIRPLANE_MODE_ON)));


        /// ring status
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        if (audioManager != null) {
            ring_status = getBoolean(audioManager.getStreamVolume(AudioManager.STREAM_RING));// zero when mute

        }

        /// gps status
        final LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null) {
            gps_status = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        }

        /// auto rotate status
        rotate_status = getBoolean(android.provider.Settings.System.getInt(context.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, 0));


        HashMap<String, Boolean> _map = new HashMap<String, Boolean>();
        _map.put("wifi_status", wifi_status);
        _map.put("bluetooth_status", bluetooth_status);
        _map.put("airplanemode_status", airplanemode_status);
        _map.put("ring_status", ring_status);
        _map.put("gps_status", gps_status);
        _map.put("rotate_status", rotate_status);
//        _map.put("vibrate_status", vibrate_status);

        return _map;

    }

    /// Set setting values in the shared preference
    public void setPreferenceValues(Context context, HashMap<String, Boolean> _map) {
        SharedPreferences pref = context.getSharedPreferences("system_settings", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        if (_map.containsKey("wifi_status")) {
            editor.putBoolean("wifi_status", _map.get("wifi_status"));
        }
        if (_map.containsKey("bluetooth_status")) {
            editor.putBoolean("bluetooth_status", _map.get("bluetooth_status"));
        }
        if (_map.containsKey("airplanemode_status")) {
            editor.putBoolean("airplanemode_status", _map.get("airplanemode_status"));
        }
        if (_map.containsKey("ring_status")) {
            editor.putBoolean("ring_status", _map.get("ring_status"));
        }
        if (_map.containsKey("gps_status")) {
            editor.putBoolean("gps_status", _map.get("gps_status"));
        }
        if (_map.containsKey("rotate_status")) {
            editor.putBoolean("rotate_status", _map.get("rotate_status"));
        }
//        if (_map.containsKey("vibrate_status")) {
//            editor.putBoolean("vibrate_status", _map.get("vibrate_status"));
//        }

        editor.apply();


    }

    private boolean getBoolean(int a) {
        return a != 0;
    }


    /// Return the lock is ON or OFF
    public boolean getLockValue(Context context) {

        HashMap<String, Boolean> preference = getPreferenceValues(context);
        HashMap<String, Boolean> system = getSystemValues(context);

        return preference.equals(system);

    }

    /// Open Alert Dialog which change the preference settings
    public void openSettings(final Context context) {

        ArrayList<Item> itemList = new ArrayList<Item>();


        HashMap<String, Boolean> preference = getPreferenceValues(context);


        itemList.add(new Item("wifi_status", preference.get("wifi_status")));
        itemList.add(new Item("bluetooth_status", preference.get("bluetooth_status")));
        itemList.add(new Item("airplanemode_status", preference.get("airplanemode_status")));
        itemList.add(new Item("ring_status", preference.get("ring_status")));
        itemList.add(new Item("gps_status", preference.get("gps_status")));
        itemList.add(new Item("rotate_status", preference.get("rotate_status")));
//        itemList.add(new Item("vibrate_status", preference.get("vibrate_status")));

        final ItemAdapter adapter = new ItemAdapter(context, itemList);


        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle("Change Settings");
        builder.setAdapter(adapter, null);

        AlertDialog dialog = builder.create();
        dialog.show();

    }
}
