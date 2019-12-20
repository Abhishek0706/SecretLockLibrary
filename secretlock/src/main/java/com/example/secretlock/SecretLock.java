package com.example.secretlock;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.example.secretlock.adapters.ItemAdapter;
import com.example.secretlock.models.Item;

import java.util.ArrayList;
import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;

public class SecretLock {


    private HashMap<String, Boolean> getPreferenceValues(Context context) {
        SharedPreferences pref = context.getSharedPreferences("system_settings", MODE_PRIVATE);
        boolean p_wifi = pref.getBoolean("wifi_status", false);
        boolean p_bluetooth = pref.getBoolean("bluetooth_status", false);
        boolean p_airplane = pref.getBoolean("airplanemode_status", false);
        boolean p_ring = pref.getBoolean("ring_status", true);
//        boolean p_vibrate = pref.getBoolean("vibrate_status", false);
//
//        Log.d(pref.getBoolean("ring_status", false));

        HashMap<String, Boolean> _map = new HashMap<String, Boolean>();
        _map.put("wifi_status", p_wifi);
        _map.put("bluetooth_status", p_bluetooth);
        _map.put("airplanemode_status", p_airplane);
        _map.put("ring_status", p_ring);
//        _map.put("vibrate_status", p_vibrate);

        return _map;

    }

    private HashMap<String, Boolean> getSystemValues(Context context) {

        boolean bluetooth_status = false;
        boolean airplanemode_status = false;
        boolean ring_status = false;
        boolean wifi_status = false;
//        boolean vibrate_status = false;

        wifi_status = getBoolean(Integer.parseInt(Settings.System.getString(context.getContentResolver(), Settings.System.WIFI_ON)));
        bluetooth_status = getBoolean(Integer.parseInt(Settings.System.getString(context.getContentResolver(), Settings.System.BLUETOOTH_ON)));
        airplanemode_status = getBoolean(Integer.parseInt(Settings.System.getString(context.getContentResolver(), Settings.System.AIRPLANE_MODE_ON)));

//        Toast.makeText(context, Settings.System.getString(context.getContentResolver(), Settings.System.VIBRATE_ON), Toast.LENGTH_LONG).show();
//        vibrate_status = getBoolean(Integer.parseInt(Settings.System.getString(context.getContentResolver(), Settings.System.VIBRATE_ON)));

        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        if (audioManager != null) {
            ring_status = getBoolean(audioManager.getStreamVolume(AudioManager.STREAM_RING));// zero when mute

        }

//        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
//        if (wifiManager != null) {
//            wifi_status = wifiManager.isWifiEnabled();
//
//        }

        HashMap<String, Boolean> _map = new HashMap<String, Boolean>();
        _map.put("wifi_status", wifi_status);
        _map.put("bluetooth_status", bluetooth_status);
        _map.put("airplanemode_status", airplanemode_status);
        _map.put("ring_status", ring_status);
//        _map.put("vibrate_status", vibrate_status);

        return _map;

    }

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
//        if (_map.containsKey("vibrate_status")) {
//            editor.putBoolean("vibrate_status", _map.get("vibrate_status"));
//        }


        editor.apply();


    }

    private boolean getBoolean(int a) {
        return a != 0;
    }

    public boolean getLockValue(Context context) {

        HashMap<String, Boolean> preference = getPreferenceValues(context);
        HashMap<String, Boolean> system = getSystemValues(context);

//        Toast.makeText(context, preference.toString(), Toast.LENGTH_SHORT).show();
//        Toast.makeText(context, system.toString(), Toast.LENGTH_SHORT).show();

        return preference.equals(system);

    }

    public void openSettings(final Context context) {

        ArrayList<Item> itemList = new ArrayList<Item>();


        HashMap<String, Boolean> preference = getPreferenceValues(context);


        itemList.add(new Item("wifi_status", preference.get("wifi_status")));
        itemList.add(new Item("bluetooth_status", preference.get("bluetooth_status")));
        itemList.add(new Item("airplanemode_status", preference.get("airplanemode_status")));
        itemList.add(new Item("ring_status", preference.get("ring_status")));
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
