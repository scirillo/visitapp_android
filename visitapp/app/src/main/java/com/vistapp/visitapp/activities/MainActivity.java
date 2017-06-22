package com.vistapp.visitapp.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.vistapp.visitapp.R;
import com.vistapp.visitapp.database.DoctorPresenter;
import com.vistapp.visitapp.fragments.DoctorFragment;
import com.vistapp.visitapp.model.DoctorModel;
import com.vistapp.visitapp.model.Location;
import com.vistapp.visitapp.utils.view.DrawerArrowDrawable;

import java.util.ArrayList;

/**
 * Created by Santiago Cirillo on 14/05/2017.
 */

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private DrawerArrowDrawable drawerArrowDrawable;
    private float offset;
    private boolean flipped;
    private DoctorPresenter doctorPresenter = DoctorPresenter.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);


      /*  final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        final ImageView imageView = (ImageView) findViewById(R.id.drawer_indicator);
        final Resources resources = getResources();


        drawerArrowDrawable = new DrawerArrowDrawable(resources);
        drawerArrowDrawable.setStrokeColor(resources.getColor(R.color.light_gray));
        imageView.setImageDrawable(drawerArrowDrawable);

        drawer.setDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override public void onDrawerSlide(View drawerView, float slideOffset) {
                offset = slideOffset;

                // Sometimes slideOffset ends up so close to but not quite 1 or 0.
                if (slideOffset >= .995) {
                    flipped = true;
                    drawerArrowDrawable.setFlip(flipped);
                } else if (slideOffset <= .005) {
                    flipped = false;
                    drawerArrowDrawable.setFlip(flipped);
                }

                drawerArrowDrawable.setParameter(offset);
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (drawer.isDrawerVisible(START)) {
                    drawer.closeDrawer(START);
                } else {
                    drawer.openDrawer(START);
                }
            }
        });

        final TextView styleButton = (TextView) findViewById(R.id.indicator_style);
        styleButton.setOnClickListener(new View.OnClickListener() {
            boolean rounded = false;

            @Override public void onClick(View v) {
                styleButton.setText(rounded //
                        ? resources.getString(R.string.rounded) //
                        : resources.getString(R.string.squared));

                rounded = !rounded;

                drawerArrowDrawable = new DrawerArrowDrawable(resources, rounded);
                drawerArrowDrawable.setParameter(offset);
                drawerArrowDrawable.setFlip(flipped);
                drawerArrowDrawable.setStrokeColor(resources.getColor(R.color.light_gray));

                imageView.setImageDrawable(drawerArrowDrawable);
            }
        });

*/
        drawer.setDrawerListener(toggle);

        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        TextView headerTitle = (TextView) navigationView.getHeaderView(0).findViewById(R.id.header_text);
        headerTitle.setText(PreferenceManager.getDefaultSharedPreferences(MainActivity.this).getString("email", ""));
        navigationView.setNavigationItemSelectedListener(this);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setOffscreenPageLimit(5);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        doctorPresenter.persistDoctorList(hardcodedList());

    }

    private ArrayList<DoctorModel> hardcodedList() {
        doctorPresenter.removeAllDoctor();
        ArrayList<DoctorModel> list = new ArrayList<>();
        DoctorModel m = new DoctorModel("1", "Juan Luis Terra", "Av.Gral Flores 2212", "Insisitr con esto", "Dr.Selby", "12:05", "098421726", "jlterra@gmail.com", "22/06/2017", true);
        DoctorModel m2 = new DoctorModel("2", "Felipe Carlos", "Av.Gral Flores 2212", "Insisitr con esto", "Particular", "13:25", "+59898421726", "fpcelestr@gmail.com", "22/06/2017", false);
        DoctorModel m3 = new DoctorModel("3", "Maria Victoria Ruiz", "Av.Gral Flores 2212", "Insisitr con esto", "CASMU", "14:00", "094500412", "mvruizsa@hotmail.com", "23/06/2017", true);
        DoctorModel m4 = new DoctorModel("4", "Oscar Dualde Jimenez", "Av.Gral Flores 2212", "Insisitr con esto", "Hospital XZZ", "15:15", "094500412", "mvruizsa@hotmail.com", "23/06/2017", false);
        DoctorModel m5 = new DoctorModel("5", "Federica Cardozo Locomona", "Av.Gral Flores 2212", "Insisitr con esto", "Centro Evangelico", "17:30", "094500412", "mvruizsa@hotmail.com", "23/06/2017", true);
        DoctorModel m6 = new DoctorModel("6", "Jimena Ruiz", "Av.Gral Flores 2212", "Insisitr con esto", "Particular", "13:25", "098421726", "jlterra@gmail.com", "24/06/2017", true);
        DoctorModel m7 = new DoctorModel("7", "Pedro Alonso", "Av.Gral Flores 2212", "Insisitr con esto", "CASMU", "14:00", "098421726", "jlterra@gmail.com", "24/06/2017", false);
        DoctorModel m8 = new DoctorModel("8", "Marcos Jimenez", "Av.Gral Flores 2212", "Insisitr con esto", "Hospital XZZ", "15:15", "098421726", "jlterra@gmail.com", "25/06/2017", true);
        DoctorModel m9 = new DoctorModel("9", "Cecilia Cardozo", "Av.Gral Flores 2212", "Insisitr con esto", "Centro Evangelico", "17:30", "098421726", "jlterra@gmail.com", "26/06/2017", false);
        Location l = new Location("Dr.Selby", -34.8173171, -56.158871);
        Location l2 = new Location("Dr.Selby", -34.902545, -56.164862);
        Location l3 = new Location("Dr.Selby", -34.903460, -56.170355);
        Location l4 = new Location("Dr.Selby", -34.892197, -56.148917);
        Location l5 = new Location("Dr.Selby", -34.889662, -56.175353);
        Location l6 = new Location("Dr.Selby", -34.904235, -56.180245);
        Location l7 = new Location("Dr.Selby", -34.909514, -56.149260);
        Location l8 = new Location("Dr.Selby", -34.885368, -56.141106);
        Location l9 = new Location("Dr.Selby", -34.885227, -56.188768);
        m.setLocation(l);
        m2.setLocation(l2);
        m3.setLocation(l3);
        m4.setLocation(l4);
        m5.setLocation(l5);
        m6.setLocation(l6);
        m7.setLocation(l7);
        m8.setLocation(l8);
        m9.setLocation(l9);
        list.add(m);
        list.add(m2);
        list.add(m3);
        list.add(m4);
        list.add(m5);
        list.add(m6);
        list.add(m7);
        list.add(m8);
        list.add(m9);
        return list;
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showLogoutDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage(getString(R.string.confirmar)).setPositiveButton(
                getString(R.string.yes),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        logout();
                    }
                }).setNegativeButton(getString(R.string.no),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();

    }

    private void logout() {
        PreferenceManager.getDefaultSharedPreferences(MainActivity.this).edit().putString("email", "").commit();
        finish();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_camera) {
            Intent intent = new Intent(MainActivity.this, UpNavActivity.class);
            intent.putExtra(UpNavActivity.FRAGMENT_ID, 1);
            startActivity(intent);
            // Handle the camera action
        } else if (id == R.id.week_1) {
            if (mViewPager != null) mViewPager.setCurrentItem(0);
        } else if (id == R.id.week_2) {
            if (mViewPager != null) mViewPager.setCurrentItem(1);
        } else if (id == R.id.week_3) {
            if (mViewPager != null) mViewPager.setCurrentItem(2);
        } else if (id == R.id.week_4) {
            if (mViewPager != null) mViewPager.setCurrentItem(3);
        } else if (id == R.id.week_5) {
            if (mViewPager != null) mViewPager.setCurrentItem(4);
        } else if (id == R.id.nav_share) {
            Intent intent = new Intent(MainActivity.this, UpNavActivity.class);
            intent.putExtra(UpNavActivity.FRAGMENT_ID, 2);
            startActivity(intent);
        } else if (id == R.id.nav_send) {
            showLogoutDialog();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private int NUM_ITEMS = 5;
        private String[] TITLE = new String[]{
                getString(R.string.monday), getString(R.string.tuesday), getString(R.string.wednesday), getString(R.string.thursday), getString(R.string.friday)};

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return DoctorFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }


        @Override
        public CharSequence getPageTitle(int position) {
            return TITLE[position];
        }
    }
}
