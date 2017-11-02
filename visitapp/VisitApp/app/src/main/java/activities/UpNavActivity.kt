package com.vistapp.visitapp.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.charly.visitapp.R

import com.vistapp.visitapp.fragments.DoctorDetailFragment
import com.vistapp.visitapp.fragments.DoctorListFragment
import com.vistapp.visitapp.fragments.WebViewFragment
import com.vistapp.visitapp.utils.Constants
import model.DoctorModel

class UpNavActivity : AppCompatActivity() {
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
            if (navId == FRAGMENT_DR_LIST) {
                toolbar.title = "Lista de medicos"
                supportFragmentManager.beginTransaction().replace(R.id.fragment, DoctorListFragment.newInstance()).commit()
            } else if (navId == WEB_VIEW) {
                supportFragmentManager.beginTransaction().replace(R.id.fragment, WebViewFragment.newInstance()).commit()
            } else if (navId == FRAGMENT_DETAIL) {
                val dayWeek = intent.getIntExtra(Constants.DAY_WEEK, 0)
                supportFragmentManager.beginTransaction().replace(R.id.fragment, DoctorDetailFragment.newInstance(DoctorModel(), true, dayWeek)).commit()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle arrow click here
        if (item.itemId === android.R.id.home) {
            if (supportFragmentManager.backStackEntryCount > 0) {
                supportFragmentManager.popBackStack()
            } else
                finish() // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item)
    }

    companion object {
        val FRAGMENT_ID = "fragment_id"
        val FRAGMENT_DR_LIST = 1
        val FRAGMENT_DETAIL = 3
        val WEB_VIEW = 2
    }

}
