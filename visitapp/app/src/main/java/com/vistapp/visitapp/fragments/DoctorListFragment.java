package com.vistapp.visitapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.vistapp.visitapp.R;
import com.vistapp.visitapp.activities.MapsActivity;
import com.vistapp.visitapp.adapters.DoctorAdapter;
import com.vistapp.visitapp.database.DoctorPresenter;
import com.vistapp.visitapp.listeners.UpdateFragmentListener;
import com.vistapp.visitapp.model.DoctorModel;
import com.vistapp.visitapp.utils.Constants;

/**
 * Created by Santiago Cirillo on 16/05/2017.
 */

public class DoctorListFragment extends Fragment implements UpdateFragmentListener {
    private RecyclerView recyclerView;
    private ProgressBar spinner;
    private DoctorAdapter adapter;
    private DoctorPresenter doctorPresenter = DoctorPresenter.getInstance();

    public static DoctorListFragment newInstance() {
        DoctorListFragment fragment = new DoctorListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void updateAdapter(DoctorModel doctorModel) {
        getFragmentManager().beginTransaction().replace(R.id.fragment, DoctorDetailFragment.newInstance(doctorModel, false, 0)).addToBackStack("").commit();
    }

    @Override
    public void itemChecked(DoctorModel doctorModel, boolean isChecked) {

    }

    @Override
    public void navigateToMaps(DoctorModel item) {
        Intent maps = new Intent(getContext(), MapsActivity.class);
        maps.putExtra(Constants.DOCTOR, item);
        startActivity(maps);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doctor_list, container, false);
        ((Toolbar) getActivity().findViewById(R.id.toolbar)).setTitle("Lista de medicos");
        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        spinner = (ProgressBar) view.findViewById(R.id.spinner);
        spinner.setVisibility(View.VISIBLE);
        adapter = new DoctorAdapter(doctorPresenter.getDoctorList(), true, false);
        adapter.setFragmentListener(this);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setVisibility(View.GONE);

        recyclerView.setAdapter(adapter);
        spinner.setVisibility(View.GONE);
        return view;
    }

}
