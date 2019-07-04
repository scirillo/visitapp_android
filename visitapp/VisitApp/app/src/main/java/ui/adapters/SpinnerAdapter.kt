package ui.adapters

import android.content.Context
import android.support.annotation.LayoutRes
import android.support.annotation.NonNull
import android.widget.ArrayAdapter
import model.DoctorModel

/**
 * Created by Santiago Cirillo on 11/2/17.
 */

class SpinnerAdapter(@NonNull context: Context, @LayoutRes resource: Int, @NonNull objects: List<DoctorModel>) :
        ArrayAdapter<DoctorModel>(context, resource, objects)

