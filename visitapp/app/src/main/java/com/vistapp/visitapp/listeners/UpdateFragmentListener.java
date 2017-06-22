package com.vistapp.visitapp.listeners;

import com.vistapp.visitapp.model.DoctorModel;

/**
 * Created by Santiago Cirillo on 15/05/2017.
 */

public interface UpdateFragmentListener {
    void updateAdapter(DoctorModel doctorModel);

    void itemChecked(DoctorModel doctorModel, boolean isChecked);

    void navigateToMaps(DoctorModel item);
}
