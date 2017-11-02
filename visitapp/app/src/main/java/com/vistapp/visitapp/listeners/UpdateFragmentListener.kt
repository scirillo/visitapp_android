package com.vistapp.visitapp.listeners

import com.vistapp.visitapp.model.DoctorModel

/**
 * Created by Santiago Cirillo on 15/05/2017.
 */

interface UpdateFragmentListener {
    fun updateAdapter(doctorModel: DoctorModel)

    fun itemChecked(doctorModel: DoctorModel, isChecked: Boolean)

    fun navigateToMaps(item: DoctorModel)
}
