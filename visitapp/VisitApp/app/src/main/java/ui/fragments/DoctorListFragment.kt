package com.vistapp.visitapp.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.charly.visitapp.R

import com.vistapp.visitapp.activities.MapsActivity
import ui.adapters.DoctorAdapter
import com.vistapp.visitapp.listeners.UpdateFragmentListener
import com.vistapp.visitapp.utils.Constants
import extensions.navigateToAddDoctor
import extensions.navigateToDetail
import model.DoctorModel
import logic.DoctorManager
import ui.views.DoctorListView

/**
 * Created by Santiago Cirillo on 16/05/2017.
 */

class DoctorListFragment : Fragment(), UpdateFragmentListener {
    private var adapter: DoctorAdapter? = null
    private lateinit var mainUI: DoctorListView

    override fun updateAdapter(doctorModel: DoctorModel) {
       navigateToDetail(fragmentManager, doctorModel)
    }

    override fun itemChecked(doctorModel: DoctorModel?, isChecked: Boolean) {

    }

    override fun navigateToMaps(item: DoctorModel?) {
        val maps = Intent(context, MapsActivity::class.java)
        maps.putExtra(Constants.DOCTOR, item)
        startActivity(maps)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity.findViewById<android.support.v7.widget.Toolbar>(R.id.toolbar) as Toolbar).title = "Lista de medicos"
        mainUI = DoctorListView()
        adapter = DoctorAdapter(DoctorManager.getInstance(context).getDoctorList(), true, false)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return mainUI.bind(this, adapter, false)
    }

    override fun onResume() {
        super.onResume()
        adapter!!.setFragmentListener(this)
    }

    companion object {
        fun newInstance(): DoctorListFragment {
            val fragment = DoctorListFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    fun navigateToAddDoctor() {
        navigateToAddDoctor(fragmentManager)
    }

}
