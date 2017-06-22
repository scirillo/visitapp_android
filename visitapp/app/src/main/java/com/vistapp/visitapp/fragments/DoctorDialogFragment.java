package com.vistapp.visitapp.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.vistapp.visitapp.R;
import com.vistapp.visitapp.listeners.DateSelectedListener;
import com.vistapp.visitapp.listeners.UpdateFragmentListener;
import com.vistapp.visitapp.model.DoctorModel;
import com.vistapp.visitapp.utils.Constants;

/**
 * Created by Santiago Cirillo on 15/05/2017.
 */

@SuppressLint("ValidFragment")
public class DoctorDialogFragment extends DialogFragment implements DateSelectedListener {
    private DoctorModel doctorModel;
    private EditText mName, mDir, mClinic, mInfo;
    private Button btnAddDoctor, mDate;
    private UpdateFragmentListener listener;

    public DoctorDialogFragment(UpdateFragmentListener listener) {
        this.listener = listener;
    }

    public DoctorDialogFragment() {
    }

    public static DoctorDialogFragment newInstance(DoctorModel doctorModel, UpdateFragmentListener listener) {
        DoctorDialogFragment f = new DoctorDialogFragment(listener);
        Bundle args = new Bundle();
        args.putSerializable(Constants.DOCTOR, doctorModel);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            doctorModel = (DoctorModel) getArguments().getSerializable(Constants.DOCTOR);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.add_doctor_dialog, container, false);
        btnAddDoctor = (Button) rootView.findViewById(R.id.btn_add_doctor);
        mName = (EditText) rootView.findViewById(R.id.item_value_name);
        mDir = (EditText) rootView.findViewById(R.id.item_value_dir);
        mClinic = (EditText) rootView.findViewById(R.id.item_value_clinic);
        mInfo = (EditText) rootView.findViewById(R.id.item_value_info);
        mDate = (Button) rootView.findViewById(R.id.item_value_date);
        mDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });
        btnAddDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewDoctor();
            }
        });
        return rootView;
    }

    public void showTimePickerDialog() {
        DialogFragment newFragment = new DateTimePickerFragment(this);
        newFragment.show(getChildFragmentManager(), "timePicker");
    }

    private void addNewDoctor() {
        if (checkFields()) {
            listener.updateAdapter(new DoctorModel("1212", mName.getText().toString(), mDir.getText().toString(), mInfo.getText().toString(), mClinic.getText().toString(), mDate.getText().toString()));
            dismiss();
        } else {
            Toast.makeText(getActivity(), "Campos incorrectos", Toast.LENGTH_LONG).show();
        }
    }

    private boolean checkFields() {
        if (TextUtils.isEmpty(mDate.getText().toString()) || TextUtils.isEmpty(mName.getText().toString()) || TextUtils.isEmpty(mDir.getText().toString()) || TextUtils.isEmpty(mClinic.getText().toString()) || TextUtils.isEmpty(mInfo.getText().toString())) {
            return false;
        }
        return true;
    }

    @Override
    public void onDateSet(int hourOfDay, int minute) {
        if (mDate != null) mDate.setText(hourOfDay + ":" + minute);
    }
}
