package com.vistapp.visitapp.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import com.vistapp.visitapp.model.DoctorModel;

import java.util.List;

/**
 * Created by Santiago Cirillo on 20/05/2017.
 */

public class SpinnerAdapter extends ArrayAdapter<DoctorModel> {
    public SpinnerAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<DoctorModel> objects) {
        super(context, resource, objects);
    }
/*
    public String itemToString(DoctorModel item) {
        return item.getId();
    }

    public DoctorModel stringToItem(CharSequence toString) {
        return stringToItem(toString.toString());
    }

    public DoctorModel stringToItem(String toString) {
        for (int i = 0; i < getCount(); i++) {
            DoctorModel item = getItem(i);
            if (itemToString(item).equalsIgnoreCase(toString)) {
                return item;
            }
        }
        return null;
    }
*/
}
