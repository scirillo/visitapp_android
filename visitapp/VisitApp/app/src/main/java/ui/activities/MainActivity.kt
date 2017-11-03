package ui.activities

import ui.BaseActivity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import com.charly.visitapp.R
import com.vistapp.visitapp.activities.FRAGMENT_ID
import com.vistapp.visitapp.activities.UpNavActivity
import com.vistapp.visitapp.fragments.DoctorFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import logic.DoctorManager
import extensions.hardcodedList
import io.realm.Realm
import io.realm.RealmQuery
import model.User

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null
    private var mViewPager: ViewPager? = null
    private var tabLayout: TabLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        val realm = Realm.getDefaultInstance()

        val savedUser: User? = RealmQuery.createQuery(realm, User::class.java).findFirst()
        nav_view.setNavigationItemSelectedListener(this)
        val navigationView = findViewById <android.support.design.widget.NavigationView>(R.id.nav_view) as NavigationView
        val headerTitle = navigationView.getHeaderView(0).findViewById<TextView>(R.id.header_text) as TextView
        if (savedUser != null) {
            headerTitle.text = savedUser.mEmail
        }
        navigationView.setNavigationItemSelectedListener(this)

        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        // Set up the ViewPager with the sections adapter.
        mViewPager =  findViewById<android.support.v4.view.ViewPager>(R.id.container) as ViewPager
        mViewPager!!.setOffscreenPageLimit(5)
        mViewPager!!.setAdapter(mSectionsPagerAdapter)

        tabLayout = findViewById<android.support.design.widget.TabLayout>(R.id.tabs) as TabLayout
        tabLayout!!.setupWithViewPager(mViewPager)

        DoctorManager.getInstance(this).persistDoctorList(hardcodedList(this))
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
                intent.putExtra(FRAGMENT_ID, 1)
                startActivity(intent)            }
            R.id.week_1 -> {
                if (mViewPager != null) mViewPager!!.currentItem = 0
            }
            R.id.week_2 -> {
                if (mViewPager != null) mViewPager!!.currentItem = 1
            }
            R.id.week_3 -> {
                if (mViewPager != null) mViewPager!!.currentItem = 2
            }
            R.id.week_4 -> {
                if (mViewPager != null) mViewPager!!.currentItem = 3
            }
            R.id.week_5 -> {
                if (mViewPager != null) mViewPager!!.currentItem = 4
            }
            R.id.nav_share -> {
                val intent = Intent(this@MainActivity, UpNavActivity::class.java)
                intent.putExtra(FRAGMENT_ID, 2)
                startActivity(intent)
            }
            R.id.nav_send -> {
                showLogoutDialog()
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun showLogoutDialog() {
        val builder = AlertDialog.Builder(this@MainActivity)
        builder.setMessage(getString(R.string.confirmar)).setPositiveButton(
                getString(R.string.yes)
        ) { _, _ -> logout() }.setNegativeButton(getString(R.string.no)
        ) { _, _ -> }.show()
    }

    private fun logout() {
        val realm = Realm.getDefaultInstance()
        realm.executeTransaction { realm ->
            realm.clear(User::class.java)
        }
        finish()
        startActivity(Intent(this@MainActivity, LogInActivity::class.java))
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
