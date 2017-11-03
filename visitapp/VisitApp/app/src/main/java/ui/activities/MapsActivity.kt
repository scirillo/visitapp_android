package com.vistapp.visitapp.activities

import ui.BaseActivity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.ImageView
import com.charly.visitapp.R

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.vistapp.visitapp.fragments.DoctorDetailFragment
import com.vistapp.visitapp.utils.Constants
import model.DoctorModel
import logic.DoctorManager

class MapsActivity : BaseActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    override fun onMarkerClick(marker: Marker?): Boolean {
        // Retrieve the data from the marker.
        var doctor =  marker!!.tag as DoctorModel
        supportFragmentManager.beginTransaction().replace(R.id.fragment, DoctorDetailFragment.newInstance(doctor,
                false, 0)).commit()
        return false
    }

    private var mMap: GoogleMap? = null
    private var doctor: DoctorModel? = null
    private var allDoctors: List<DoctorModel>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        val toolbar = findViewById<android.support.v7.widget.Toolbar>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        // add back arrow to toolbar
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        doctor = intent!!.getSerializableExtra(Constants.DOCTOR) as DoctorModel

        allDoctors = DoctorManager.getInstance(baseContext).getDoctorList()
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle arrow click here
        if (item.itemId == android.R.id.home) {
            if (supportFragmentManager.backStackEntryCount > 0) {
                supportFragmentManager.popBackStack()
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
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        // Add a marker in Sydney and move the camera
        val sydney = LatLng(doctor!!.location.latitude, doctor!!.location.longitude)
        //    LatLng sydney = new LatLng(-33.852, 151.211);
       var marker =  mMap!!.addMarker(MarkerOptions()
                .position(sydney)
                .title(doctor!!.name)
                .snippet(doctor!!.clinic)
                .icon(BitmapDescriptorFactory
                        .fromResource(R.mipmap.ic_launcher)))
        marker.tag = doctor
        marker.showInfoWindow()
        mMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15.0f))

        for (model in allDoctors!!) {
            mMap!!.addMarker(MarkerOptions()
                    .position(LatLng(model.location.latitude, model.location.longitude))
                    .title(model.name)
                    .snippet(model.clinic)
                    .icon(BitmapDescriptorFactory
                            .fromResource(R.mipmap.ic_launcher)))

        }

        val waze = findViewById<android.widget.ImageView>(R.id.btn_waze) as ImageView
        waze.setOnClickListener{openWaze()}
        // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }


    private fun openWaze() {
        try {
            val url = "waze://?ll=" + doctor!!.location.latitude + "," + doctor!!.location.latitude + "&navigate=yes"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        } catch (ex: ActivityNotFoundException) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.waze"))
            startActivity(intent)
        }

    }
}
