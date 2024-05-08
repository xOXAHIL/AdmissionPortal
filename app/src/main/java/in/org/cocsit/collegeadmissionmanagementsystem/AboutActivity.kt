package `in`.org.cocsit.collegeadmissionmanagementsystem

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.location.LocationManagerCompat.isLocationEnabled
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.Locale

class AboutActivity : AppCompatActivity() {

    lateinit var tvLatitude: TextView
    lateinit var tvLongitude: TextView
    lateinit var tvCountryName: TextView
    lateinit var tvLocality: TextView
    lateinit var tvAddress: TextView
    lateinit var btnLocation: Button
    lateinit var button2:Button

    private lateinit var mFusedLocationProviderClient: FusedLocationProviderClient
    private val permissionID = 2

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        tvLatitude = findViewById(R.id.tv1)
        tvLongitude = findViewById(R.id.tv2)
        tvCountryName = findViewById(R.id.tv3)
        tvLocality = findViewById(R.id.tv4)
        tvAddress = findViewById(R.id.tv5)
        btnLocation = findViewById(R.id.btn)
        button2 = findViewById(R.id.btn1)

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        btnLocation.setOnClickListener {
            getLocation()
        }
        button2.setOnClickListener {
            getLocationForMap()
        }
    }
    @SuppressLint("MissingPermission","SetTextI18n")
    private fun getLocation(){
        if(checkPermissions()){
            if(isLocationEnabled()){
                mFusedLocationProviderClient.lastLocation.addOnSuccessListener {
                        location: Location? ->
                    location?.let{
                        val geocoder = Geocoder(this,Locale.getDefault())
                        val list:List<Address> = geocoder.getFromLocation(location.latitude, location.longitude,1)!!
                        tvLatitude.text = "Latitude\n${list[0].latitude}"
                        tvLongitude.text = "Longitude\n${list[0].longitude}"
                        tvCountryName.text = "Country Name\n${list[0].countryName}"
                        tvLocality.text = "Locality\n${list[0].locality}"
                        tvAddress.text = "Address\n${list[0].getAddressLine(0)}"
                    }
                }
            } else{
                Toast.makeText(this,"Please turn on location ", Toast.LENGTH_SHORT).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        }else{
            requestPermissions()
        }
    }
    private fun getLocationForMap() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return
                }
                mFusedLocationProviderClient.lastLocation.addOnSuccessListener { location: Location? ->
                    location?.let {
                        val geocoder = Geocoder(this, Locale.getDefault())
                        val list: List<Address> = geocoder.getFromLocation(location.latitude, location.longitude, 1)!!

                        val latitude: Double = list[0].latitude.toDouble()
                        val longitude: Double = list[0].longitude.toDouble()
                        val data = "geo:$latitude,$longitude"

                        val intentMap = Intent(Intent.ACTION_VIEW, Uri.parse(data))
                        startActivity(intentMap)
                    }
                }
            } else {
                Toast.makeText(this, "Please turn on location", Toast.LENGTH_SHORT).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }
    private fun isLocationEnabled():Boolean{
        val locationManager:LocationManager = getSystemService(Context.LOCATION_SERVICE)as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }
    private fun checkPermissions():Boolean{
        if(ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            return true
        }
        return false
    }

    private fun requestPermissions(){
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ),
            permissionID
        )
    }

    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if(requestCode == permissionID){
            if((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)){
                getLocation()
            }
        }
    }
}