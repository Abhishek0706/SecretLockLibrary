package com.example.secretlock.models;

import java.util.HashMap;
import java.util.Map;

public class Item {

    private String name;
    private Boolean value;

    private static final Map<String, String> name_map = new HashMap<String, String>() {{
        put("wifi_status", "wifi");
        put("bluetooth_status", "bluetooth");
        put("airplanemode_status", "airplane mode");
        put("ring_status", "ring");
        put("vibrate_status", "vibrate");
        put("gps_status", "location");

    }};

    public Item(String name, Boolean value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getValue() {
        return value;
    }

    public void setValue(Boolean value) {
        this.value = value;
    }

    public String getXmlName() {
        return name_map.get(this.name);
    }
}
