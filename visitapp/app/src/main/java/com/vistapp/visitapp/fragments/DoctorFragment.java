package com.vistapp.visitapp.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.vistapp.visitapp.R;
import com.vistapp.visitapp.activities.MapsActivity;
import com.vistapp.visitapp.activities.UpNavActivity;
import com.vistapp.visitapp.adapters.DoctorAdapter;
import com.vistapp.visitapp.database.DoctorPresenter;
import com.vistapp.visitapp.listeners.RecyclerOnClickListener;
import com.vistapp.visitapp.listeners.UpdateFragmentListener;
import com.vistapp.visitapp.model.DoctorModel;
import com.vistapp.visitapp.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Santiago Cirillo on 14/05/2017.
 */

public class DoctorFragment extends Fragment implements UpdateFragmentListener {
    private RecyclerView recyclerView;
    private ProgressBar spinner;
    private DoctorAdapter adapter;
    private FloatingActionButton fab;
    private List<DoctorModel> deleteList = new ArrayList<>();
    private DoctorPresenter doctorPresenter = DoctorPresenter.getInstance();
    private int day;
    final View.OnClickListener addDeleteListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (fab.getTag() != null) {
                showDialog((DoctorModel) fab.getTag());
            } else {
                openAddDoctorDialog();
            }
        }
    };

    public static DoctorFragment newInstance(int day) {
        DoctorFragment fragment = new DoctorFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.DAY_WEEK, day);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            day = getArguments().getInt(Constants.DAY_WEEK);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doctor_list, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        spinner = (ProgressBar) view.findViewById(R.id.spinner);
        spinner.setVisibility(View.VISIBLE);
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(addDeleteListener);
        spinner.setVisibility(View.GONE);
        return view;
    }

    private void openAddDoctorDialog() {
       /* DoctorDialogFragment alertDialog = DoctorDialogFragment.newInstance(null, DoctorFragment.this);
        alertDialog.show(getFragmentManager(), "fragment_alert");*/
        Intent intent = new Intent(getContext(), UpNavActivity.class);
        intent.putExtra(UpNavActivity.FRAGMENT_ID, UpNavActivity.FRAGMENT_DETAIL);
        intent.putExtra(Constants.DAY_WEEK, day);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter = new DoctorAdapter(doctorPresenter.getDoctorAssignedPerDay(day), false, true);
        adapter.setOnClickListener(new RecyclerOnClickListener() {
            @Override
            public void onClick(View v, int position) {
                //showDialog(adapter.getValues().get(position));
            }
        });
        adapter.setFragmentListener(this);
        recyclerView.setAdapter(adapter);
    }

    private void showDialog(final DoctorModel model) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(getString(R.string.confirmar)).setPositiveButton(
                getString(R.string.yes),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteAdapterItem(model);
                    }
                }).setNegativeButton(getString(R.string.no),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).show();
    }

    private void deleteAdapterItem(DoctorModel model) {
        if (!deleteList.isEmpty()) {
            doctorPresenter.removeAssignedDoctors(deleteList, day);
        }
        adapter.addAll(doctorPresenter.getDoctorAssignedPerDay(day));
        fab.setTag(null);
        fab.setImageResource(R.drawable.ic_person_add_black_24dp);
    }

    @Override
    public void updateAdapter(DoctorModel doctorModel) {
        doctorPresenter.addDoctor(doctorModel);
        adapter.addAll(doctorPresenter.getDoctorList());
    }

    @Override
    public void itemChecked(final DoctorModel doctorModel, boolean isChecked) {
        if (doctorModel != null) {
            if(isChecked) {
                if (!deleteList.contains(doctorModel)) deleteList.add(doctorModel);
            }else{
                if (deleteList.contains(doctorModel)) deleteList.remove(doctorModel);
            }
        }
        fab.setOnClickListener(addDeleteListener);
        if (isChecked) {
            fab.setTag(doctorModel);
            fab.setImageResource(R.drawable.ic_assignment_turned_in_black_24dp);
        } else {
            fab.setTag(null);
            fab.setImageResource(R.drawable.ic_person_add_black_24dp);
        }
    }

    @Override
    public void navigateToMaps(DoctorModel item) {
        Intent maps = new Intent(getContext(), MapsActivity.class);
        maps.putExtra(Constants.DOCTOR, item);
        startActivity(maps);
    }
}
