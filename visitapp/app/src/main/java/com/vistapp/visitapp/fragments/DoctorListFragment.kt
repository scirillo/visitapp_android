package com.vistapp.visitapp.fragments

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar

import com.vistapp.visitapp.R
import com.vistapp.visitapp.activities.MapsActivity
import com.vistapp.visitapp.adapters.DoctorAdapter
import com.vistapp.visitapp.database.DoctorPresenter
import com.vistapp.visitapp.listeners.UpdateFragmentListener
import com.vistapp.visitapp.model.DoctorModel
import com.vistapp.visitapp.utils.Constants

/**
 * Created by Santiago Cirillo on 16/05/2017.
 */

class DoctorListFragment : Fragment(), UpdateFragmentListener {
    private var recyclerView: RecyclerView? = null
    private var spinner: ProgressBar? = null
    private var adapter: DoctorAdapter? = null
    private val doctorPresenter = DoctorPresenter.getInstance()

    @Override
    fun updateAdapter(doctorModel: DoctorModel) {
        getFragmentManager().beginTransaction().replace(R.id.fragment, DoctorDetailFragment.newInstance(doctorModel, false, 0)).addToBackStack("").commit()
    }

    @Override
    fun itemChecked(doctorModel: DoctorModel, isChecked: Boolean) {

    }

    @Override
    fun navigateToMaps(item: DoctorModel) {
        val maps = Intent(getContext(), MapsActivity::class.java)
        maps.putExtra(Constants.DOCTOR, item)
        startActivity(maps)
    }

    @Override
    fun onCreateView(inflater: LayoutInflater, container: ViewGroup,
                     savedInstanceState: Bundle): View {
        val view = inflater.inflate(R.layout.fragment_doctor_list, container, false)
        (getActivity().findViewById(R.id.toolbar) as Toolbar).setTitle("Lista de medicos")
        recyclerView = view.findViewById(R.id.list) as RecyclerView
        recyclerView!!.setLayoutManager(LinearLayoutManager(view.getContext()))
        spinner = view.findViewById(R.id.spinner) as ProgressBar
        spinner!!.setVisibility(View.VISIBLE)
        adapter = DoctorAdapter(doctorPresenter.getDoctorList(), true, false)
        adapter!!.setFragmentListener(this)
        val fab = view.findViewById(R.id.fab) as FloatingActionButton
        fab.setVisibility(View.GONE)

        recyclerView!!.setAdapter(adapter)
        spinner!!.setVisibility(View.GONE)
        return view
    }

    companion object {

        fun newInstance(): DoctorListFragment {
            val fragment = DoctorListFragment()
            val args = Bundle()
            fragment.setArguments(args)
            return fragment
        }
    }

}
