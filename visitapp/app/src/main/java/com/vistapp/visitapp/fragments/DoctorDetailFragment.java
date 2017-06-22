package com.vistapp.visitapp.fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.vistapp.visitapp.R;
import com.vistapp.visitapp.adapters.SpinnerAdapter;
import com.vistapp.visitapp.database.DoctorPresenter;
import com.vistapp.visitapp.listeners.DateSelectedListener;
import com.vistapp.visitapp.listeners.UpdateFragmentListener;
import com.vistapp.visitapp.model.DoctorModel;
import com.vistapp.visitapp.utils.Constants;

import java.util.List;

/**
 * Created by Santiago Cirillo on 16/05/2017.
 */

public class DoctorDetailFragment extends Fragment implements DateSelectedListener {

    private TextView mName, mDir, mClinic, mInfo, mPhone, mEmail;
    private Button btnAddDoctor, mDate;
    private UpdateFragmentListener listener;
    private DoctorPresenter doctorPresenter = DoctorPresenter.getInstance();
    private DoctorModel doctorModel;
    private boolean editable;
    private int day;
    private Spinner spDoctors;

    public DoctorDetailFragment() {
    }

    public static Fragment newInstance(DoctorModel doctorModel, boolean editable, int day) {
        DoctorDetailFragment fragment = new DoctorDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(Constants.DOCTOR, doctorModel);
        args.putBoolean(Constants.EDITABLE, editable);
        args.putInt(Constants.DAY_WEEK, day);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            doctorModel = (DoctorModel) getArguments().getSerializable(Constants.DOCTOR);
            editable = getArguments().getBoolean(Constants.EDITABLE);
            day = getArguments().getInt(Constants.DAY_WEEK);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail_doctor, container, false);

        ((Toolbar) getActivity().findViewById(R.id.toolbar)).setTitle("Dr. " + doctorModel.getName());
        mName = (TextView) rootView.findViewById(R.id.item_value_name);
        mDir = (TextView) rootView.findViewById(R.id.item_value_dir);
        mClinic = (TextView) rootView.findViewById(R.id.item_value_clinic);
        mInfo = (TextView) rootView.findViewById(R.id.item_value_info);
        mDate = (Button) rootView.findViewById(R.id.item_value_date);
        mPhone = (TextView) rootView.findViewById(R.id.item_value_phone);
        spDoctors = (Spinner) rootView.findViewById(R.id.sp_doctors);
        mEmail = (TextView) rootView.findViewById(R.id.item_value_email);
        btnAddDoctor = (Button) rootView.findViewById(R.id.btn_add_doctor);
        btnAddDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewDoctor();
            }
        });
        mDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });
        return rootView;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onViewCreated(View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setDataFields();
        disableFields();


        // Creating adapter for spinner
        List<DoctorModel> list = doctorPresenter.getDoctorListPerDay(day);
        //THIS TITLE
        DoctorModel title = new DoctorModel();
        title.setName("Select");

        final SpinnerAdapter dataAdapter = new SpinnerAdapter(getContext(), android.R.layout.simple_spinner_item, list);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spDoctors.setAdapter(dataAdapter);
      /*  spDoctors.addTextChangedListener(new OnSpinnerItemSelectedListener() {
            @Override
            protected void onItemSelected(String id) {
                doctorModel = dataAdapter.stringToItem(id);
                setDataFields();
                // do something
            }
        });*/
        spDoctors.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                doctorModel = dataAdapter.getItem(position);
                setDataFields();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setDataFields() {
        mName.setText(doctorModel.getName());
        mDir.setText(doctorModel.getDirection());
        mClinic.setText(doctorModel.getClinic());
        mInfo.setText(doctorModel.getNote());
        mDate.setText(doctorModel.getTime());
        mPhone.setText(doctorModel.getPhone());
        mEmail.setText(doctorModel.getEmail());
    }

    private void disableFields() {
        mName.setEnabled(editable);
        mDir.setEnabled(editable);
        mClinic.setEnabled(editable);
        mInfo.setEnabled(editable);
        mDate.setEnabled(editable);
        mPhone.setEnabled(editable);
        mEmail.setEnabled(editable);
        if (editable) {
            spDoctors.setVisibility(View.VISIBLE);
            btnAddDoctor.setVisibility(View.VISIBLE);
        } else {
            spDoctors.setVisibility(View.GONE);
            btnAddDoctor.setVisibility(View.GONE);
        }
    }

    private void addNewDoctor() {
        if (checkFields()) {
            int dayW = 22 + day;
            DoctorModel newModel = new DoctorModel(doctorModel.getId(), mName.getText().toString(), mDir.getText().toString(), mInfo.getText().toString(),
                    mClinic.getText().toString(), mDate.getText().toString(), mPhone.getText().toString(), mEmail.getText().toString(), dayW + "/06/2017", true);
            doctorPresenter.addDoctor(newModel);
            getActivity().finish();
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

    public void showTimePickerDialog() {
        DialogFragment newFragment = new DateTimePickerFragment(this);
        newFragment.show(getChildFragmentManager(), "timePicker");
    }

    @Override
    public void onDateSet(int hourOfDay, int minute) {
        if (mDate != null) mDate.setText(hourOfDay + ":" + minute);
    }
}
