package com.vistapp.visitapp.fragments

import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast

import com.vistapp.visitapp.R
import com.vistapp.visitapp.adapters.SpinnerAdapter
import com.vistapp.visitapp.database.DoctorPresenter
import com.vistapp.visitapp.listeners.DateSelectedListener
import com.vistapp.visitapp.listeners.UpdateFragmentListener
import com.vistapp.visitapp.model.DoctorModel
import com.vistapp.visitapp.utils.Constants

/**
 * Created by Santiago Cirillo on 16/05/2017.
 */

class DoctorDetailFragment : Fragment(), DateSelectedListener {

    private var mName: TextView? = null
    private var mDir: TextView? = null
    private var mClinic: TextView? = null
    private var mInfo: TextView? = null
    private var mPhone: TextView? = null
    private var mEmail: TextView? = null
    private var btnAddDoctor: Button? = null
    private var mDate: Button? = null
    private val listener: UpdateFragmentListener? = null
    private val doctorPresenter = DoctorPresenter.getInstance()
    private var doctorModel: DoctorModel? = null
    private var editable: Boolean = false
    private var day: Int = 0
    private var spDoctors: Spinner? = null

    @Override
    fun onCreate(@Nullable savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        if (getArguments() != null) {
            doctorModel = getArguments().getSerializable(Constants.DOCTOR) as DoctorModel
            editable = getArguments().getBoolean(Constants.EDITABLE)
            day = getArguments().getInt(Constants.DAY_WEEK)
        }
    }

    @Override
    fun onCreateView(inflater: LayoutInflater, container: ViewGroup,
                     savedInstanceState: Bundle): View {
        val rootView = inflater.inflate(R.layout.fragment_detail_doctor, container, false)

        (getActivity().findViewById(R.id.toolbar) as Toolbar).setTitle("Dr. " + doctorModel!!.getName())
        mName = rootView.findViewById(R.id.item_value_name) as TextView
        mDir = rootView.findViewById(R.id.item_value_dir) as TextView
        mClinic = rootView.findViewById(R.id.item_value_clinic) as TextView
        mInfo = rootView.findViewById(R.id.item_value_info) as TextView
        mDate = rootView.findViewById(R.id.item_value_date) as Button
        mPhone = rootView.findViewById(R.id.item_value_phone) as TextView
        spDoctors = rootView.findViewById(R.id.sp_doctors) as Spinner
        mEmail = rootView.findViewById(R.id.item_value_email) as TextView
        btnAddDoctor = rootView.findViewById(R.id.btn_add_doctor) as Button
        btnAddDoctor!!.setOnClickListener(object : View.OnClickListener() {
            @Override
            fun onClick(v: View) {
                addNewDoctor()
            }
        })
        mDate!!.setOnClickListener(object : View.OnClickListener() {
            @Override
            fun onClick(v: View) {
                showTimePickerDialog()
            }
        })
        return rootView
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    fun onViewCreated(view: View, @Nullable savedInstanceState: Bundle) {
        super.onViewCreated(view, savedInstanceState)
        setDataFields()
        disableFields()


        // Creating adapter for spinner
        val list = doctorPresenter.getDoctorListPerDay(day)
        //THIS TITLE
        val title = DoctorModel()
        title.setName("Select")

        val dataAdapter = SpinnerAdapter(getContext(), android.R.layout.simple_spinner_item, list)
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // attaching data adapter to spinner
        spDoctors!!.setAdapter(dataAdapter)
        /*  spDoctors.addTextChangedListener(new OnSpinnerItemSelectedListener() {
            @Override
            protected void onItemSelected(String id) {
                doctorModel = dataAdapter.stringToItem(id);
                setDataFields();
                // do something
            }
        });*/
        spDoctors!!.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener() {
            @Override
            fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                doctorModel = dataAdapter.getItem(position)
                setDataFields()
            }

            @Override
            fun onNothingSelected(parent: AdapterView<*>) {

            }
        })
    }

    private fun setDataFields() {
        mName!!.setText(doctorModel!!.getName())
        mDir!!.setText(doctorModel!!.getDirection())
        mClinic!!.setText(doctorModel!!.getClinic())
        mInfo!!.setText(doctorModel!!.getNote())
        mDate!!.setText(doctorModel!!.getTime())
        mPhone!!.setText(doctorModel!!.getPhone())
        mEmail!!.setText(doctorModel!!.getEmail())
    }

    private fun disableFields() {
        mName!!.setEnabled(editable)
        mDir!!.setEnabled(editable)
        mClinic!!.setEnabled(editable)
        mInfo!!.setEnabled(editable)
        mDate!!.setEnabled(editable)
        mPhone!!.setEnabled(editable)
        mEmail!!.setEnabled(editable)
        if (editable) {
            spDoctors!!.setVisibility(View.VISIBLE)
            btnAddDoctor!!.setVisibility(View.VISIBLE)
        } else {
            spDoctors!!.setVisibility(View.GONE)
            btnAddDoctor!!.setVisibility(View.GONE)
        }
    }

    private fun addNewDoctor() {
        if (checkFields()) {
            val dayW = 22 + day
            val newModel = DoctorModel(doctorModel!!.getId(), mName!!.getText().toString(), mDir!!.getText().toString(), mInfo!!.getText().toString(),
                    mClinic!!.getText().toString(), mDate!!.getText().toString(), mPhone!!.getText().toString(), mEmail!!.getText().toString(), dayW.toString() + "/06/2017", true)
            doctorPresenter.addDoctor(newModel)
            getActivity().finish()
        } else {
            Toast.makeText(getActivity(), "Campos incorrectos", Toast.LENGTH_LONG).show()
        }
    }

    private fun checkFields(): Boolean {
        return if (TextUtils.isEmpty(mDate!!.getText().toString()) || TextUtils.isEmpty(mName!!.getText().toString()) || TextUtils.isEmpty(mDir!!.getText().toString()) || TextUtils.isEmpty(mClinic!!.getText().toString()) || TextUtils.isEmpty(mInfo!!.getText().toString())) {
            false
        } else true
    }

    fun showTimePickerDialog() {
        val newFragment = DateTimePickerFragment(this)
        newFragment.show(getChildFragmentManager(), "timePicker")
    }

    @Override
    fun onDateSet(hourOfDay: Int, minute: Int) {
        if (mDate != null) mDate!!.setText(hourOfDay.toString() + ":" + minute)
    }

    companion object {

        fun newInstance(doctorModel: DoctorModel, editable: Boolean, day: Int): Fragment {
            val fragment = DoctorDetailFragment()
            val args = Bundle()
            args.putSerializable(Constants.DOCTOR, doctorModel)
            args.putBoolean(Constants.EDITABLE, editable)
            args.putInt(Constants.DAY_WEEK, day)
            fragment.setArguments(args)
            return fragment
        }
    }
}
