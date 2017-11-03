package com.vistapp.visitapp.fragments

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.charly.visitapp.R
import com.vistapp.visitapp.activities.FRAGMENT_DETAIL
import com.vistapp.visitapp.activities.FRAGMENT_ID
import com.vistapp.visitapp.activities.MapsActivity
import com.vistapp.visitapp.activities.UpNavActivity
import com.vistapp.visitapp.adapters.DoctorAdapter
import com.vistapp.visitapp.listeners.RecyclerOnClickListener
import com.vistapp.visitapp.listeners.UpdateFragmentListener
import com.vistapp.visitapp.utils.Constants
import model.DoctorModel
import logic.DoctorManager

/**
 * Created by Santiago Cirillo on 14/05/2017.
 */

class DoctorFragment : android.support.v4.app.Fragment(), UpdateFragmentListener {

    override fun updateAdapter(doctorModel: DoctorModel?) {
        if (doctorModel != null) {
            DoctorManager.getInstance(context)!!.addDoctor(doctorModel)
        }
        adapter!!.addAll(DoctorManager.getInstance(context).getDoctorList())
    }

    override fun itemChecked(doctorModel: DoctorModel?, isChecked: Boolean) {
        if (doctorModel != null) {
            if (isChecked) {
                if (!deleteList.contains(doctorModel)) deleteList.add(doctorModel)
            } else {
                if (deleteList.contains(doctorModel)) deleteList.remove(doctorModel)
            }
        }
        fab!!.setOnClickListener(addDeleteListener)
        if (isChecked) {
            fab!!.tag = doctorModel
            fab!!.setImageResource(R.drawable.ic_assignment_turned_in_black_24dp)
        } else {
            fab!!.tag = null
            fab!!.setImageResource(R.drawable.ic_person_add_black_24dp)
        }
    }

    override fun navigateToMaps(item: DoctorModel?) {
        val maps = Intent(context, MapsActivity::class.java)
        maps.putExtra(Constants.DOCTOR, item)
        startActivity(maps)
    }

    private var recyclerView: RecyclerView? = null
    private var spinner: ProgressBar? = null
    private var adapter: DoctorAdapter? = null
    private var fab: FloatingActionButton? = null
    private val deleteList = arrayListOf<DoctorModel>()
    private var day: Int = 0
    private val addDeleteListener: View.OnClickListener = View.OnClickListener {
        if (fab!!.tag != null) {
            showDialog(fab!!.tag as DoctorModel)
        } else {
            openAddDoctorDialog()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            day = arguments.getInt(Constants.DAY_WEEK)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_doctor_list, container, false)
        recyclerView = view.findViewById(R.id.list)
        recyclerView!!.layoutManager = LinearLayoutManager(view.context)
        spinner = view.findViewById(R.id.spinner)
        spinner!!.visibility = View.VISIBLE
        fab = view.findViewById(R.id.fab)
        fab!!.setOnClickListener(addDeleteListener)
        spinner!!.visibility = View.GONE
        return view
    }

    private fun openAddDoctorDialog() {
       /* val alertDialog = DoctorDialogFragment.newInstance(null, DoctorFragment.this);
        alertDialog.show(fragmentManager, "fragment_alert");*/
        val intent = Intent(context, UpNavActivity::class.java)
        intent.putExtra(FRAGMENT_ID, FRAGMENT_DETAIL)
        intent.putExtra(Constants.DAY_WEEK, day)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        adapter = DoctorAdapter(DoctorManager.getInstance(context)!!.getDoctorAssignedPerDay(day), false, true)
        adapter!!.setOnClickListener(object : RecyclerOnClickListener{
            override fun onClick(v: View, position: Int) {
                showDialog(adapter!!.values[position])
            }
        })
        adapter!!.setFragmentListener(this)
        recyclerView!!.adapter = adapter
    }

    private fun showDialog(model: DoctorModel) {
        val builder = AlertDialog.Builder(context)
        builder.setMessage(getString(R.string.confirmar)).setPositiveButton(
                getString(R.string.yes)
        ) { _, _ -> deleteAdapterItem(model) }.setNegativeButton(getString(R.string.no)
        ) { _, _ -> }.show()
    }

    private fun deleteAdapterItem(model: DoctorModel) {
        if (!deleteList.isEmpty()) {
            DoctorManager.getInstance(context)!!.removeAssignedDoctors(deleteList, day)
        }
        adapter!!.addAll(DoctorManager.getInstance(context)!!.getDoctorAssignedPerDay(day))
        fab!!.tag = null
        fab!!.setImageResource(R.drawable.ic_person_add_black_24dp)
    }

    companion object {
        fun newInstance(day: Int): DoctorFragment {
            val fragment = DoctorFragment()
            val args = Bundle()
            args.putInt(Constants.DAY_WEEK, day)
            fragment.arguments = args
            return fragment
        }
    }
}
