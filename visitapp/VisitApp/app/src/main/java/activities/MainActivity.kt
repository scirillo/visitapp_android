package activities

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
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import com.charly.visitapp.R
import com.vistapp.visitapp.activities.UpNavActivity
import com.vistapp.visitapp.fragments.DoctorFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import logic.DoctorManager
import model.DoctorModel
import model.Location
import utils.addDay
import utils.dateNow

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

   /* override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }*/

    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null
    private var mViewPager: ViewPager? = null
    private var tabLayout: TabLayout? = null
        private val offset: Float = 0.toFloat()
    private val flipped: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
        val navigationView = findViewById <android.support.design.widget.NavigationView>(R.id.nav_view) as NavigationView
        val headerTitle = navigationView.getHeaderView(0).findViewById<TextView>(R.id.header_text) as TextView
        headerTitle.setText(PreferenceManager.getDefaultSharedPreferences(this@MainActivity).getString("email", ""))
        navigationView.setNavigationItemSelectedListener(this)

        mSectionsPagerAdapter = SectionsPagerAdapter(getSupportFragmentManager())

        // Set up the ViewPager with the sections adapter.
        mViewPager =  findViewById<android.support.v4.view.ViewPager>(R.id.container) as ViewPager
        mViewPager!!.setOffscreenPageLimit(5)
        mViewPager!!.setAdapter(mSectionsPagerAdapter)

        tabLayout = findViewById<android.support.design.widget.TabLayout>(R.id.tabs) as TabLayout
        tabLayout!!.setupWithViewPager(mViewPager)

        DoctorManager.getInstance(this).persistDoctorList(hardcodedList())

    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                val intent = Intent(this@MainActivity, UpNavActivity::class.java)
                intent.putExtra(UpNavActivity.FRAGMENT_ID, 1)
                startActivity(intent)            }
            R.id.week_1 -> {
                if (mViewPager != null) mViewPager!!.setCurrentItem(0)
            }
            R.id.week_2 -> {
                if (mViewPager != null) mViewPager!!.setCurrentItem(1)

            }
            R.id.week_3 -> {
                if (mViewPager != null) mViewPager!!.setCurrentItem(2)

            }
            R.id.week_4 -> {
                if (mViewPager != null) mViewPager!!.setCurrentItem(3)

            }
            R.id.week_5 -> {
                if (mViewPager != null) mViewPager!!.setCurrentItem(4)

            }
            R.id.nav_share -> {
                val intent = Intent(this@MainActivity, UpNavActivity::class.java)
                intent.putExtra(UpNavActivity.FRAGMENT_ID, 2)
                startActivity(intent)
            }
            R.id.nav_send -> {
                showLogoutDialog()
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun hardcodedList(): ArrayList<DoctorModel> {
        DoctorManager.getInstance(this).removeAllDoctor()
        val list = ArrayList<DoctorModel>()
        val m = DoctorModel("1", "Juan Luis Terra", "Av.Gral Flores 2212", "Insisitr con esto",
                "Dr.Selby", "12:05", "098421726", "jlterra@gmail.com", addDay(0), true)
        val m2 = DoctorModel("2", "Felipe Carlos", "Av.Gral Flores 2212", "Insisitr con esto",
                "Particular", "13:25", "+59898421726", "fpcelestr@gmail.com", addDay(0), false)
        val m3 = DoctorModel("3", "Maria Victoria Ruiz", "Av.Gral Flores 2212", "Insisitr con esto",
                "CASMU", "14:00", "094500412", "mvruizsa@hotmail.com", addDay(1), true)
        val m4 = DoctorModel("4", "Oscar Dualde Jimenez", "Av.Gral Flores 2212", "Insisitr con esto",
                "Hospital XZZ", "15:15", "094500412", "mvruizsa@hotmail.com", addDay(1), false)
        val m5 = DoctorModel("5", "Federica Cardozo Locomona", "Av.Gral Flores 2212", "Insisitr con esto",
                "Centro Evangelico", "17:30", "094500412", "mvruizsa@hotmail.com", addDay(1), true)
        val m6 = DoctorModel("6", "Jimena Ruiz", "Av.Gral Flores 2212", "Insisitr con esto",
                "Particular", "13:25", "098421726", "jlterra@gmail.com", addDay(2), true)
        val m7 = DoctorModel("7", "Pedro Alonso", "Av.Gral Flores 2212", "Insisitr con esto",
                "CASMU", "14:00", "098421726", "jlterra@gmail.com", addDay(2), false)
        val m8 = DoctorModel("8", "Marcos Jimenez", "Av.Gral Flores 2212", "Insisitr con esto",
                "Hospital XZZ", "15:15", "098421726", "jlterra@gmail.com", addDay(3), true)
        val m9 = DoctorModel("9", "Cecilia Cardozo", "Av.Gral Flores 2212", "Insisitr con esto",
                "Centro Evangelico", "17:30", "098421726", "jlterra@gmail.com", addDay(4), false)
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

    private fun showLogoutDialog() {
        val builder = AlertDialog.Builder(this@MainActivity)
        builder.setMessage(getString(R.string.confirmar)).setPositiveButton(
                getString(R.string.yes)
        ) { _, _ -> logout() }.setNegativeButton(getString(R.string.no)
        ) { _, _ -> }.show()

    }

    private fun logout() {
        PreferenceManager.getDefaultSharedPreferences(this@MainActivity).edit().putString("email", "").commit()
        finish()
       // val intent = Intent(this@MainActivity, LoginActivity::class.java)
        startActivity(intent)
    }

    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getCount(): Int {
         return 5
        }
       private val TITLE = arrayOf<String>(getString(R.string.monday), getString(R.string.tuesday),
                getString(R.string.wednesday), getString(R.string.thursday), getString(R.string.friday))

        override fun getItem(position: Int): Fragment {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return DoctorFragment.newInstance(position)
        }

        override fun getPageTitle(position: Int): CharSequence {
            return TITLE[position]
        }
    }
}
