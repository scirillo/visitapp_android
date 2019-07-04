package com.vistapp.visitapp.activities

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.design.widget.NavigationView
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView

import com.vistapp.visitapp.R
import com.vistapp.visitapp.database.DoctorPresenter
import com.vistapp.visitapp.fragments.DoctorFragment
import com.vistapp.visitapp.model.DoctorModel
import com.vistapp.visitapp.model.Location
import com.vistapp.visitapp.utils.view.DrawerArrowDrawable

import java.util.ArrayList

/**
 * Created by Santiago Cirillo on 14/05/2017.
 */

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null
    private var mViewPager: ViewPager? = null
    private var tabLayout: TabLayout? = null
    private val drawerArrowDrawable: DrawerArrowDrawable? = null
    private val offset: Float = 0.toFloat()
    private val flipped: Boolean = false
    private val doctorPresenter = DoctorPresenter.getInstance()

    @Override
    protected fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)

        drawer.setDrawerListener(toggle)

        toggle.syncState()

        val navigationView = findViewById(R.id.nav_view) as NavigationView
        val headerTitle = navigationView.getHeaderView(0).findViewById(R.id.header_text) as TextView
        headerTitle.setText(PreferenceManager.getDefaultSharedPreferences(this@MainActivity).getString("email", ""))
        navigationView.setNavigationItemSelectedListener(this)

        mSectionsPagerAdapter = SectionsPagerAdapter(getSupportFragmentManager())

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container) as ViewPager
        mViewPager!!.setOffscreenPageLimit(5)
        mViewPager!!.setAdapter(mSectionsPagerAdapter)

        tabLayout = findViewById(R.id.tabs) as TabLayout
        tabLayout!!.setupWithViewPager(mViewPager)

        doctorPresenter.persistDoctorList(hardcodedList())

    }

    private fun hardcodedList(): ArrayList<DoctorModel> {
        doctorPresenter.removeAllDoctor()
        val list = ArrayList()
        val m = DoctorModel("1", "Juan Luis Terra", "Av.Gral Flores 2212", "Insisitr con esto", "Dr.Selby", "12:05", "098421726", "jlterra@gmail.com", "22/06/2017", true)
        val m2 = DoctorModel("2", "Felipe Carlos", "Av.Gral Flores 2212", "Insisitr con esto", "Particular", "13:25", "+59898421726", "fpcelestr@gmail.com", "22/06/2017", false)
        val m3 = DoctorModel("3", "Maria Victoria Ruiz", "Av.Gral Flores 2212", "Insisitr con esto", "CASMU", "14:00", "094500412", "mvruizsa@hotmail.com", "23/06/2017", true)
        val m4 = DoctorModel("4", "Oscar Dualde Jimenez", "Av.Gral Flores 2212", "Insisitr con esto", "Hospital XZZ", "15:15", "094500412", "mvruizsa@hotmail.com", "23/06/2017", false)
        val m5 = DoctorModel("5", "Federica Cardozo Locomona", "Av.Gral Flores 2212", "Insisitr con esto", "Centro Evangelico", "17:30", "094500412", "mvruizsa@hotmail.com", "23/06/2017", true)
        val m6 = DoctorModel("6", "Jimena Ruiz", "Av.Gral Flores 2212", "Insisitr con esto", "Particular", "13:25", "098421726", "jlterra@gmail.com", "24/06/2017", true)
        val m7 = DoctorModel("7", "Pedro Alonso", "Av.Gral Flores 2212", "Insisitr con esto", "CASMU", "14:00", "098421726", "jlterra@gmail.com", "24/06/2017", false)
        val m8 = DoctorModel("8", "Marcos Jimenez", "Av.Gral Flores 2212", "Insisitr con esto", "Hospital XZZ", "15:15", "098421726", "jlterra@gmail.com", "25/06/2017", true)
        val m9 = DoctorModel("9", "Cecilia Cardozo", "Av.Gral Flores 2212", "Insisitr con esto", "Centro Evangelico", "17:30", "098421726", "jlterra@gmail.com", "26/06/2017", false)
        val l = Location("Dr.Selby", -34.8173171, -56.158871)
        val l2 = Location("Dr.Selby", -34.902545, -56.164862)
        val l3 = Location("Dr.Selby", -34.903460, -56.170355)
        val l4 = Location("Dr.Selby", -34.892197, -56.148917)
        val l5 = Location("Dr.Selby", -34.889662, -56.175353)
        val l6 = Location("Dr.Selby", -34.904235, -56.180245)
        val l7 = Location("Dr.Selby", -34.909514, -56.149260)
        val l8 = Location("Dr.Selby", -34.885368, -56.141106)
        val l9 = Location("Dr.Selby", -34.885227, -56.188768)
        m.setLocation(l)
        m2.setLocation(l2)
        m3.setLocation(l3)
        m4.setLocation(l4)
        m5.setLocation(l5)
        m6.setLocation(l6)
        m7.setLocation(l7)
        m8.setLocation(l8)
        m9.setLocation(l9)
        list.add(m)
        list.add(m2)
        list.add(m3)
        list.add(m4)
        list.add(m5)
        list.add(m6)
        list.add(m7)
        list.add(m8)
        list.add(m9)
        return list
    }


    @Override
    fun onBackPressed() {
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    @Override
    fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu)
        return true
    }

    @Override
    fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.getItemId()


        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)

    }

    private fun showLogoutDialog() {
        val builder = AlertDialog.Builder(this@MainActivity)
        builder.setMessage(getString(R.string.confirmar)).setPositiveButton(
                getString(R.string.yes),
                object : DialogInterface.OnClickListener() {
                    @Override
                    fun onClick(dialogInterface: DialogInterface, i: Int) {
                        logout()
                    }
                }).setNegativeButton(getString(R.string.no),
                object : DialogInterface.OnClickListener() {
                    @Override
                    fun onClick(dialogInterface: DialogInterface, i: Int) {

                    }
                }).show()

    }

    private fun logout() {
        PreferenceManager.getDefaultSharedPreferences(this@MainActivity).edit().putString("email", "").commit()
        finish()
        val intent = Intent(this@MainActivity, LoginActivity::class.java)
        startActivity(intent)
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.getItemId()
        if (id == R.id.nav_camera) {
            val intent = Intent(this@MainActivity, UpNavActivity::class.java)
            intent.putExtra(UpNavActivity.FRAGMENT_ID, 1)
            startActivity(intent)
            // Handle the camera action
        } else if (id == R.id.week_1) {
            if (mViewPager != null) mViewPager!!.setCurrentItem(0)
        } else if (id == R.id.week_2) {
            if (mViewPager != null) mViewPager!!.setCurrentItem(1)
        } else if (id == R.id.week_3) {
            if (mViewPager != null) mViewPager!!.setCurrentItem(2)
        } else if (id == R.id.week_4) {
            if (mViewPager != null) mViewPager!!.setCurrentItem(3)
        } else if (id == R.id.week_5) {
            if (mViewPager != null) mViewPager!!.setCurrentItem(4)
        } else if (id == R.id.nav_share) {
            val intent = Intent(this@MainActivity, UpNavActivity::class.java)
            intent.putExtra(UpNavActivity.FRAGMENT_ID, 2)
            startActivity(intent)
        } else if (id == R.id.nav_send) {
            showLogoutDialog()
        }

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        @get:Override
        val count = 5
        private val TITLE = arrayOf<String>(getString(R.string.monday), getString(R.string.tuesday), getString(R.string.wednesday), getString(R.string.thursday), getString(R.string.friday))

        @Override
        fun getItem(position: Int): Fragment {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return DoctorFragment.newInstance(position)
        }


        @Override
        fun getPageTitle(position: Int): CharSequence {
            return TITLE[position]
        }
    }
}
