package com.vistapp.visitapp.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v4.app.DialogFragment
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.charly.visitapp.R

import com.vistapp.visitapp.listeners.DateSelectedListener
import com.vistapp.visitapp.listeners.UpdateFragmentListener
import com.vistapp.visitapp.utils.Constants
import model.DoctorModel

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
    private var listener: UpdateFragmentListener? = null

    constructor(listener: UpdateFragmentListener) {
        this.listener = listener
    }

    constructor()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            doctorModel = arguments.getSerializable(Constants.DOCTOR) as DoctorModel
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater!!.inflate(R.layout.add_doctor_dialog, container, false)
        btnAddDoctor = rootView.findViewById(R.id.btn_add_doctor)
        mName = rootView.findViewById(R.id.item_value_name)
        mDir = rootView.findViewById(R.id.item_value_dir)
        mClinic = rootView.findViewById(R.id.item_value_clinic)
        mInfo = rootView.findViewById(R.id.item_value_info)
        mDate = rootView.findViewById(R.id.item_value_date)
        mDate!!.setOnClickListener { showTimePickerDialog() }
        btnAddDoctor!!.setOnClickListener { addNewDoctor() }
        return rootView
    }

    fun showTimePickerDialog() {
        val newFragment = DateTimePickerFragment(this)
        newFragment.show(childFragmentManager, "timePicker")
    }

    private fun addNewDoctor() {
        if (checkFields()) {
            listener!!.updateAdapter(DoctorModel("1212", mName!!.text.toString(), mDir!!.text.toString(),
                    mInfo!!.text.toString(), mClinic!!.text.toString(), mDate!!.text.toString()))
            dismiss()
        } else {
            Toast.makeText(activity, "Campos incorrectos", Toast.LENGTH_LONG).show()
        }
    }

    private fun checkFields(): Boolean {
        return !(TextUtils.isEmpty(mDate!!.text.toString()) || TextUtils.isEmpty(mName!!.text.toString()) || TextUtils.isEmpty(mDir!!.text.toString())
                || TextUtils.isEmpty(mClinic!!.text.toString()) || TextUtils.isEmpty(mInfo!!.text.toString()))
    }


    override fun onDateSet(hourOfDay: Int, minute: Int) {
        if (mDate != null) mDate!!.text = hourOfDay.toString() + ":" + minute
    }

    companion object {

        fun newInstance(doctorModel: DoctorModel?, listener: UpdateFragmentListener): DoctorDialogFragment {
            val f = DoctorDialogFragment(listener)
            val args = Bundle()
            args.putSerializable(Constants.DOCTOR, doctorModel)
            f.arguments = args
            return f
        }
    }
}
