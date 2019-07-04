package com.vistapp.visitapp.fragments

import android.R
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vistapp.visitapp.utils.Constants
import model.DoctorModel
import logic.DoctorManager
import model.Location
import ui.adapters.SpinnerAdapter
import extensions.addDay
import ui.views.DetailFragmentView

/**
 * Created by Santiago Cirillo on 16/05/2017.
 */

class DoctorDetailFragment : Fragment() {
    private lateinit var doctorModel: DoctorModel
    private var editable: Boolean = false
    private var day: Int = 0
    private lateinit var mainUI: DetailFragmentView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainUI = DetailFragmentView()
        if (arguments != null) {
            doctorModel = arguments.getSerializable(Constants.DOCTOR) as DoctorModel
            editable = arguments.getBoolean(Constants.EDITABLE)
            day = arguments.getInt(Constants.DAY_WEEK)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return mainUI.bind(this, doctorModel, editable)
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val list = DoctorManager.getInstance(context).getDoctorListPerDay(day)
        //THIS TITLE
        val title = DoctorModel()
        title.name = "Select"
        val uriAdapter = SpinnerAdapter(context, R.layout.simple_spinner_item, list)
        uriAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        mainUI.spDoctors.adapter = uriAdapter
    }


    fun addDoctor(newDoctor: DoctorModel){
        val l4 = Location("Dr.Selby", -34.892197, -56.148917)
        newDoctor.date = addDay(day)
        newDoctor.location = l4
        DoctorManager.getInstance(context).addDoctor(newDoctor)
        activity.finish()
    }

    companion object {
        fun newInstance(doctorModel: DoctorModel, editable: Boolean, day: Int): Fragment {
            val fragment = DoctorDetailFragment()
            val args = Bundle()
            args.putSerializable(Constants.DOCTOR, doctorModel)
            args.putBoolean(Constants.EDITABLE, editable)
            args.putInt(Constants.DAY_WEEK, day)
            fragment.arguments = args
            return fragment
        }
    }
}
