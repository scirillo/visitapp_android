package com.vistapp.visitapp.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem

import com.vistapp.visitapp.R
import com.vistapp.visitapp.fragments.DoctorDetailFragment
import com.vistapp.visitapp.fragments.DoctorListFragment
import com.vistapp.visitapp.fragments.WebViewFragment
import com.vistapp.visitapp.model.DoctorModel
import com.vistapp.visitapp.utils.Constants

class UpNavActivity : AppCompatActivity() {
    private val doctorModel: DoctorModel? = null

    @Override
    protected fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.up_nav_activity)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true)
            getSupportActionBar().setDisplayShowHomeEnabled(true)
        }

        if (getIntent() != null) {
            val navId = getIntent().getIntExtra(FRAGMENT_ID, 1)
            if (navId == FRAGMENT_DR_LIST) {
                toolbar.setTitle("Lista de medicos")
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, DoctorListFragment.newInstance()).commit()
            } else if (navId == WEB_VIEW) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, WebViewFragment.newInstance()).commit()
            } else if (navId == FRAGMENT_DETAIL) {
                val dayWeek = getIntent().getIntExtra(Constants.DAY_WEEK, 0)
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, DoctorDetailFragment.newInstance(DoctorModel(), true, dayWeek)).commit()
            }
        }
    }


    @Override
    fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle arrow click here
        if (item.getItemId() === android.R.id.home) {
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportFragmentManager().popBackStack()
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
