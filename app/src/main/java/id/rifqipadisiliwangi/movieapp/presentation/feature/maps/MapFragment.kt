package id.rifqipadisiliwangi.movieapp.presentation.feature.maps

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import id.rifqipadisiliwangi.movieapp.R
import id.rifqipadisiliwangi.movieapp.databinding.FragmentMapBinding
import id.rifqipadisiliwangi.movieapp.presentation.common.viewmodel.prelogin.PreloginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.IOException
import java.util.Locale

class MapFragment : Fragment(), OnMapReadyCallback,
    GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener {

    private var _binding: FragmentMapBinding? = null
    private val viewModel : PreloginViewModel by viewModel()
    private val binding get() = _binding!!

    private lateinit var googleMap: GoogleMap
    private lateinit var locationManager: LocationManager
    private var marker: Marker? = null

    private var provinceName: String? = null
    private var regencyName: String? = null
    private var districtName: String? = null
    private var realLocation: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListener()
        val mapView = binding.mapView
        mapView.onCreate(savedInstanceState)
        mapView.onResume()
        mapView.getMapAsync(this)

        locationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        googleMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
        googleMap.setOnMarkerClickListener(this)
        googleMap.setOnMapClickListener(this)

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            if (isGPSEnabled()) {
                googleMap.isMyLocationEnabled = true
                if (!provinceName.isNullOrEmpty() && !regencyName.isNullOrEmpty() && !districtName.isNullOrEmpty()) {
                    updateMapWithLocation()
                } else {
                    moveCameraToDefaultLocation()
                }
            } else {
                moveCameraToDefaultLocation()
                showGPSDisabledDialog()
            }
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }

    private fun updateMapWithLocation() {
        val address = "$provinceName, $regencyName, $districtName"
        val geocoder = Geocoder(requireContext())
        try {
            val addresses = geocoder.getFromLocationName(address, 1)
            if (addresses != null) {
                if (addresses.isNotEmpty()) {
                    val location = addresses[0]
                    val latLng = LatLng(location.latitude, location.longitude)
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12f))
                } else {
                    showToast(resources.getString(R.string.string_address_not_found))
                    moveCameraToDefaultLocation()
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
            showToast("${resources.getString(R.string.string_somethind_went_wrong_from_geocoding)}  ${e.message}")
            moveCameraToDefaultLocation()
        }
    }

    private fun moveCameraToDefaultLocation() {
        val indonesiaLocation = LatLng(-0.7893, 113.9213)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(indonesiaLocation, 5f))
    }

    private fun isGPSEnabled(): Boolean {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    override fun onMarkerClick(p0: Marker): Boolean {
        return false
    }

    override fun onMapClick(latLng: LatLng) {
        marker?.remove()
        marker = googleMap.addMarker(MarkerOptions().position(latLng).title(resources.getString(R.string.string_my_location)))
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))
        getRealLocationFromLatLng(requireContext(), latLng)
    }

    private fun showGPSDisabledDialog() {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        dialogBuilder.setMessage(resources.getString(R.string.string_gps_is_disabled_enable_gps_in_the_device_settings_to_use_this_feature))
            .setCancelable(false)
            .setPositiveButton(resources.getString(R.string.string_settings)) { dialog: DialogInterface, _: Int ->
                dialog.dismiss()
                val settingsIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(settingsIntent)
            }
            .setNegativeButton(resources.getString(R.string.string_cancel)) { dialog: DialogInterface, _: Int ->
                dialog.dismiss()
            }

        val alert = dialogBuilder.create()
        alert.setOnShowListener {
            alert.getButton(AlertDialog.BUTTON_POSITIVE)?.setTextColor(
                ContextCompat.getColor(requireContext(), android.R.color.black)
            )
            alert.getButton(AlertDialog.BUTTON_NEGATIVE)?.setTextColor(
                ContextCompat.getColor(requireContext(), android.R.color.black)
            )
        }
        alert.show()
    }
    private fun getRealLocationFromLatLng(context: Context, latLng: LatLng) {
        val geocoder = Geocoder(context, Locale.getDefault())
        try {
            val addresses: List<Address> = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1) as MutableList
            if (addresses.isNotEmpty()) {
                val address: Address = addresses[0]
                val addressStringBuilder = StringBuilder()
                for (i in 0..address.maxAddressLineIndex) {
                    addressStringBuilder.append(address.getAddressLine(i)).append("\n")
                }
                realLocation = addressStringBuilder.toString()
                viewModel.addLocation(realLocation.toString())
            } else {
                Toast.makeText(context,resources.getString(R.string.string_real_location_not_found), Toast.LENGTH_SHORT).show()
            }
        } catch (e: IOException) {
            e.printStackTrace()
            showToast("${resources.getString(R.string.string_somethind_went_wrong_from_geocoding)}  ${e.message}")
        }
    }

    private fun setOnClickListener(){
        binding.btnSubmit.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }


    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }
}
