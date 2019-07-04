package com.vistapp.visitapp.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import com.vistapp.visitapp.R
import com.vistapp.visitapp.listeners.DateSelectedListener
import com.vistapp.visitapp.listeners.UpdateFragmentListener
import com.vistapp.visitapp.model.DoctorModel
import com.vistapp.visitapp.utils.Constants

/**
 * Created by Santiago Cirillo on 15/05/2017.
 */

@SuppressLint("ValidFragment")
class DoctorDialogFragment : DialogFragment, DateSelectedListener {
    private var doctorModel: DoctorModel? = null
    private var mName: EditText? = null
    private var mDir: EditText? = null
    private var mClinic: EditText? = null
    private var mInfo: EditText? = null
    private var btnAddDoctor: Button? = null
    private var mDate: Button? = null
    private val listener: UpdateFragmentListener

    constructor(listener: UpdateFragmentListener) {
        this.listener = listener
    }

    constructor() {}

    @Override
    fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        if (getArguments() != null) {
            doctorModel = getArguments().getSerializable(Constants.DOCTOR) as DoctorModel
        }
    }

    @Nullable
    @Override
    fun onCreateView(inflater: LayoutInflater, @Nullable container: ViewGroup, savedInstanceState: Bundle): View {
        val rootView = inflater.inflate(R.layout.add_doctor_dialog, container, false)
        btnAddDoctor = rootView.findViewById(R.id.btn_add_doctor) as Button
        mName = rootView.findViewById(R.id.item_value_name) as EditText
        mDir = rootView.findViewById(R.id.item_value_dir) as EditText
        mClinic = rootView.findViewById(R.id.item_value_clinic) as EditText
        mInfo = rootView.findViewById(R.id.item_value_info) as EditText
        mDate = rootView.findViewById(R.id.item_value_date) as Button
        mDate!!.setOnClickListener(object : View.OnClickListener() {
            @Override
            fun onClick(v: View) {
                showTimePickerDialog()
            }
        })
        btnAddDoctor!!.setOnClickListener(object : View.OnClickListener() {
            @Override
            fun onClick(v: View) {
                addNewDoctor()
            }
        })
        return rootView
    }

    fun showTimePickerDialog() {
        val newFragment = DateTimePickerFragment(this)
        newFragment.show(getChildFragmentManager(), "timePicker")
    }

    private fun addNewDoctor() {
        if (checkFields()) {
            listener.updateAdapter(DoctorModel("1212", mName!!.getText().toString(), mDir!!.getText().toString(), mInfo!!.getText().toString(), mClinic!!.getText().toString(), mDate!!.getText().toString()))
            dismiss()
        } else {
            Toast.makeText(getActivity(), "Campos incorrectos", Toast.LENGTH_LONG).show()
        }
    }

    private fun checkFields(): Boolean {
        return if (TextUtils.isEmpty(mDate!!.getText().toString()) || TextUtils.isEmpty(mName!!.getText().toString()) || TextUtils.isEmpty(mDir!!.getText().toString()) || TextUtils.isEmpty(mClinic!!.getText().toString()) || TextUtils.isEmpty(mInfo!!.getText().toString())) {
            false
        } else true
    }

    @Override
    fun onDateSet(hourOfDay: Int, minute: Int) {
        if (mDate != null) mDate!!.setText(hourOfDay.toString() + ":" + minute)
    }

    companion object {

        fun newInstance(doctorModel: DoctorModel, listener: UpdateFragmentListener): DoctorDialogFragment {
            val f = DoctorDialogFragment(listener)
            val args = Bundle()
            args.putSerializable(Constants.DOCTOR, doctorModel)
            f.setArguments(args)
            return f
        }
    }
}
