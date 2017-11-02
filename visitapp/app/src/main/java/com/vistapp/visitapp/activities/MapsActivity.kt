package com.vistapp.visitapp.activities

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.widget.ImageView

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.vistapp.visitapp.R
import com.vistapp.visitapp.database.DoctorPresenter
import com.vistapp.visitapp.model.DoctorModel
import com.vistapp.visitapp.utils.Constants

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private var mMap: GoogleMap? = null
    private var doctor: DoctorModel? = null
    private val doctorPresenter = DoctorPresenter.getInstance()
    private var allDoctors: List<DoctorModel>? = null

    @Override
    protected fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true)
            getSupportActionBar().setDisplayShowHomeEnabled(true)
        }

        if (getIntent() != null) {
            doctor = getIntent().getSerializableExtra(Constants.DOCTOR) as DoctorModel
        }
        allDoctors = doctorPresenter.getDoctorList()
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = getSupportFragmentManager()
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
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


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        // Add a marker in Sydney and move the camera
        val sydney = LatLng(doctor!!.getLocation().getLatitude(), doctor!!.getLocation().getLongitude())
        //    LatLng sydney = new LatLng(-33.852, 151.211);
        mMap!!.addMarker(MarkerOptions()
                .position(sydney)
                .title(doctor!!.getName())
                .snippet(doctor!!.getClinic())
                .icon(BitmapDescriptorFactory
                        .fromResource(R.mipmap.ic_hospital))).showInfoWindow()
        mMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15.0f))

        for (model in allDoctors!!) {
            mMap!!.addMarker(MarkerOptions()
                    .position(LatLng(model.getLocation().getLatitude(), model.getLocation().getLongitude()))
                    .title(model.getName())
                    .snippet(model.getClinic())
                    .icon(BitmapDescriptorFactory
                            .fromResource(R.mipmap.ic_hospital)))

        }

        val waze = findViewById(R.id.btn_waze) as ImageView
        waze.setOnClickListener(object : View.OnClickListener() {
            @Override
            fun onClick(v: View) {
                openWaze()
            }
        })
        // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    private fun openWaze() {
        try {
            val url = "waze://?ll=" + doctor!!.getLocation().getLatitude() + "," + doctor!!.getLocation().getLatitude() + "&navigate=yes"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        } catch (ex: ActivityNotFoundException) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.waze"))
            startActivity(intent)
        }

    }
}
