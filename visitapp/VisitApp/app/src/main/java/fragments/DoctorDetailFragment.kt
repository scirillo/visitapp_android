package com.vistapp.visitapp.fragments

import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vistapp.visitapp.utils.Constants
import model.DoctorModel
import logic.DoctorManager
import model.Location
import utils.SpinnerAdapter
import utils.addDay
import view.DetailFragmentView

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
       // val rootView = inflater!!.inflate(R.layout.fragment_detail_doctor, container, false)
        return mainUI.bind(this, doctorModel, editable)
      /*  (activity.findViewById<android.support.v7.widget.Toolbar>(R.id.toolbar) as Toolbar).title = "Dr. " + doctorModel!!.name
        mName = rootView.findViewById(R.id.item_value_name)
        mDir = rootView.findViewById(R.id.item_value_dir)
        mClinic = rootView.findViewById(R.id.item_value_clinic)
        mInfo = rootView.findViewById(R.id.item_value_info)
        mDate = rootView.findViewById(R.id.item_value_date)
        mPhone = rootView.findViewById(R.id.item_value_phone)
        spDoctors = rootView.findViewById(R.id.sp_doctors)
        mEmail = rootView.findViewById(R.id.item_value_email)
        btnAddDoctor = rootView.findViewById(R.id.btn_add_doctor)
        btnAddDoctor!!.setOnClickListener{addNewDoctor()}
        mDate!!.setOnClickListener{ showTimePickerDialog() }*/
    //    return rootView
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
      //  setDataFields()
     //   disableFields()
        // Creating adapter for spinner
        val list = DoctorManager.getInstance(context).getDoctorListPerDay(day)
        //THIS TITLE
        val title = DoctorModel()
        title.name = "Select"
        // initialize URI spinner
        val uriAdapter =  SpinnerAdapter(context, android.R.layout.simple_spinner_item, list)
        uriAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        mainUI.spDoctors.adapter = uriAdapter
       // }
    }
/*


    fun showTimePickerDialog() {
        val newFragment = DateTimePickerFragment(this)
        newFragment.show(childFragmentManager, "timePicker")
    }
*/


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
