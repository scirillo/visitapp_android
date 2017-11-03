package com.vistapp.visitapp.activities

import ui.BaseActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.charly.visitapp.R

import com.vistapp.visitapp.fragments.DoctorDetailFragment
import com.vistapp.visitapp.fragments.DoctorListFragment
import com.vistapp.visitapp.fragments.WebViewFragment
import com.vistapp.visitapp.utils.Constants
import model.DoctorModel
var FRAGMENT_ID = "fragment_id"
var FRAGMENT_DR_LIST = 1
var FRAGMENT_DETAIL = 3
var WEB_VIEW = 2
class UpNavActivity : BaseActivity() {
    private val doctorModel: DoctorModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.up_nav_activity)
        val toolbar = findViewById<android.support.v7.widget.Toolbar>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        // add back arrow to toolbar
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        if (intent != null) {
            val navId = intent.getIntExtra(FRAGMENT_ID, 1)
            when (navId) {
                FRAGMENT_DR_LIST -> {
                    toolbar.title = "Lista de medicos"
                    supportFragmentManager.beginTransaction().replace(R.id.fragment, DoctorListFragment.newInstance()).commit()
                }
                WEB_VIEW -> supportFragmentManager.beginTransaction().replace(R.id.fragment, WebViewFragment.newInstance()).commit()
                FRAGMENT_DETAIL -> {
                    val dayWeek = intent.getIntExtra(Constants.DAY_WEEK, 0)
                    supportFragmentManager.beginTransaction().replace(R.id.fragment, DoctorDetailFragment.newInstance(DoctorModel(), true, dayWeek)).commit()
                }
            }
        }
    }
}
