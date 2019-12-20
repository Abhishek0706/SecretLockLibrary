package com.example.secretlock.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.secretlock.R;
import com.example.secretlock.SecretLock;
import com.example.secretlock.models.Item;

import java.util.ArrayList;
import java.util.HashMap;

public class ItemAdapter extends ArrayAdapter<Item> {


    private Context mContext;
    private ArrayList<Item> itemList;


    public ItemAdapter(@NonNull Context context, ArrayList<Item> itemList) {
        super(context, 0, itemList);
        mContext = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);

        }

        final Item item = getItem(position);

        final TextView name = convertView.findViewById(R.id.textview_name);
        final Switch value = convertView.findViewById(R.id.switch_value);

        if (item != null) {
            name.setText(item.getXmlName());
            value.setOnCheckedChangeListener(null);
            value.setChecked(item.getValue());
        }

        value.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                HashMap<String, Boolean> _map = new HashMap<>();
                _map.put(item.getName(), isChecked);
                new SecretLock().setPreferenceValues(getContext(), _map);
            }
        });

        return convertView;
    }

    public HashMap<String, Boolean> getAllItems() {
        HashMap<String, Boolean> _map = new HashMap<String, Boolean>();
        for (int i = 0; i < getCount(); i++) {
            Item item = getItem(i);
            if (item != null) {
                _map.put(item.getName(), item.getValue());
            }

        }

        return _map;
    }
}
