package com.vistapp.visitapp.adapters

import android.content.Context
import android.support.annotation.LayoutRes
import android.widget.ArrayAdapter

import com.vistapp.visitapp.model.DoctorModel

/**
 * Created by Santiago Cirillo on 20/05/2017.
 */

class SpinnerAdapter(@NonNull context: Context, @LayoutRes resource: Int, @NonNull objects: List<DoctorModel>) : ArrayAdapter<DoctorModel>(context, resource, objects)/*
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
