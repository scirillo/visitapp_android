package com.vistapp.visitapp.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.charly.visitapp.R

import com.vistapp.visitapp.activities.MapsActivity
import com.vistapp.visitapp.adapters.DoctorAdapter
import com.vistapp.visitapp.listeners.UpdateFragmentListener
import com.vistapp.visitapp.utils.Constants
import model.DoctorModel
import logic.DoctorManager

/**
 * Created by Santiago Cirillo on 16/05/2017.
 */

class DoctorListFragment : Fragment(), UpdateFragmentListener {
    private var recyclerView: RecyclerView? = null
    private var spinner: ProgressBar? = null
    private var adapter: DoctorAdapter? = null

    override fun updateAdapter(doctorModel: DoctorModel?) {
        fragmentManager.beginTransaction().replace(R.id.fragment,
                DoctorDetailFragment.newInstance(doctorModel!!, false, 0)).
                addToBackStack("").commit()
    }

   override fun itemChecked(doctorModel: DoctorModel?, isChecked: Boolean) {

    }
    override fun navigateToMaps(item: DoctorModel?) {
        val maps = Intent(context, MapsActivity::class.java)
        maps.putExtra(Constants.DOCTOR, item)
        startActivity(maps)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_doctor_list, container, false)
        (activity.findViewById<android.support.v7.widget.Toolbar>(R.id.toolbar) as Toolbar).title = "Lista de medicos"
        recyclerView = view.findViewById(R.id.list)
        recyclerView!!.layoutManager = LinearLayoutManager(view.context)
        spinner = view.findViewById(R.id.spinner)
        spinner!!.visibility = View.VISIBLE
        adapter = DoctorAdapter(DoctorManager.getInstance(context).getDoctorList(), true, false)
        adapter!!.setFragmentListener(this)

        recyclerView!!.adapter = adapter
        spinner!!.visibility = View.GONE
        return view
    }

    companion object {

        fun newInstance(): DoctorListFragment {
            val fragment = DoctorListFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

}
