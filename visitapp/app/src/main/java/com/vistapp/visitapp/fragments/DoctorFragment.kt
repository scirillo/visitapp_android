package com.vistapp.visitapp.fragments

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar

import com.vistapp.visitapp.R
import com.vistapp.visitapp.activities.MapsActivity
import com.vistapp.visitapp.activities.UpNavActivity
import com.vistapp.visitapp.adapters.DoctorAdapter
import com.vistapp.visitapp.database.DoctorPresenter
import com.vistapp.visitapp.listeners.RecyclerOnClickListener
import com.vistapp.visitapp.listeners.UpdateFragmentListener
import com.vistapp.visitapp.model.DoctorModel
import com.vistapp.visitapp.utils.Constants

import java.util.ArrayList

/**
 * Created by Santiago Cirillo on 14/05/2017.
 */

class DoctorFragment : Fragment(), UpdateFragmentListener {
    private var recyclerView: RecyclerView? = null
    private var spinner: ProgressBar? = null
    private var adapter: DoctorAdapter? = null
    private var fab: FloatingActionButton? = null
    private val deleteList = ArrayList()
    private val doctorPresenter = DoctorPresenter.getInstance()
    private var day: Int = 0
    internal val addDeleteListener: View.OnClickListener = object : View.OnClickListener() {
        @Override
        fun onClick(v: View) {
            if (fab!!.getTag() != null) {
                showDialog(fab!!.getTag() as DoctorModel)
            } else {
                openAddDoctorDialog()
            }
        }
    }

    @Override
    fun onCreate(@Nullable savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        if (getArguments() != null) {
            day = getArguments().getInt(Constants.DAY_WEEK)
        }
    }

    @Override
    fun onCreateView(inflater: LayoutInflater, container: ViewGroup,
                     savedInstanceState: Bundle): View {
        val view = inflater.inflate(R.layout.fragment_doctor_list, container, false)
        recyclerView = view.findViewById(R.id.list) as RecyclerView
        recyclerView!!.setLayoutManager(LinearLayoutManager(view.getContext()))
        spinner = view.findViewById(R.id.spinner) as ProgressBar
        spinner!!.setVisibility(View.VISIBLE)
        fab = view.findViewById(R.id.fab) as FloatingActionButton
        fab!!.setOnClickListener(addDeleteListener)
        spinner!!.setVisibility(View.GONE)
        return view
    }

    private fun openAddDoctorDialog() {
        /* DoctorDialogFragment alertDialog = DoctorDialogFragment.newInstance(null, DoctorFragment.this);
        alertDialog.show(getFragmentManager(), "fragment_alert");*/
        val intent = Intent(getContext(), UpNavActivity::class.java)
        intent.putExtra(UpNavActivity.FRAGMENT_ID, UpNavActivity.FRAGMENT_DETAIL)
        intent.putExtra(Constants.DAY_WEEK, day)
        startActivity(intent)
    }

    @Override
    fun onResume() {
        super.onResume()
        adapter = DoctorAdapter(doctorPresenter.getDoctorAssignedPerDay(day), false, true)
        adapter!!.setOnClickListener(object : RecyclerOnClickListener() {
            @Override
            fun onClick(v: View, position: Int) {
                //showDialog(adapter.getValues().get(position));
            }
        })
        adapter!!.setFragmentListener(this)
        recyclerView!!.setAdapter(adapter)
    }

    private fun showDialog(model: DoctorModel) {
        val builder = AlertDialog.Builder(getContext())
        builder.setMessage(getString(R.string.confirmar)).setPositiveButton(
                getString(R.string.yes),
                object : DialogInterface.OnClickListener() {
                    @Override
                    fun onClick(dialogInterface: DialogInterface, i: Int) {
                        deleteAdapterItem(model)
                    }
                }).setNegativeButton(getString(R.string.no),
                object : DialogInterface.OnClickListener() {
                    @Override
                    fun onClick(dialogInterface: DialogInterface, i: Int) {
                    }
                }).show()
    }

    private fun deleteAdapterItem(model: DoctorModel) {
        if (!deleteList.isEmpty()) {
            doctorPresenter.removeAssignedDoctors(deleteList, day)
        }
        adapter!!.addAll(doctorPresenter.getDoctorAssignedPerDay(day))
        fab!!.setTag(null)
        fab!!.setImageResource(R.drawable.ic_person_add_black_24dp)
    }

    @Override
    fun updateAdapter(doctorModel: DoctorModel) {
        doctorPresenter.addDoctor(doctorModel)
        adapter!!.addAll(doctorPresenter.getDoctorList())
    }

    @Override
    fun itemChecked(doctorModel: DoctorModel?, isChecked: Boolean) {
        if (doctorModel != null) {
            if (isChecked) {
                if (!deleteList.contains(doctorModel)) deleteList.add(doctorModel)
            } else {
                if (deleteList.contains(doctorModel)) deleteList.remove(doctorModel)
            }
        }
        fab!!.setOnClickListener(addDeleteListener)
        if (isChecked) {
            fab!!.setTag(doctorModel)
            fab!!.setImageResource(R.drawable.ic_assignment_turned_in_black_24dp)
        } else {
            fab!!.setTag(null)
            fab!!.setImageResource(R.drawable.ic_person_add_black_24dp)
        }
    }

    @Override
    fun navigateToMaps(item: DoctorModel) {
        val maps = Intent(getContext(), MapsActivity::class.java)
        maps.putExtra(Constants.DOCTOR, item)
        startActivity(maps)
    }

    companion object {

        fun newInstance(day: Int): DoctorFragment {
            val fragment = DoctorFragment()
            val args = Bundle()
            args.putInt(Constants.DAY_WEEK, day)
            fragment.setArguments(args)
            return fragment
        }
    }
}
