package com.quoders.apps.qmoves.stopDetail.location

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.quoders.apps.qmoves.R
import com.quoders.apps.qmoves.data.Stop
import com.quoders.apps.qmoves.route.RouteFragment
import com.quoders.apps.qmoves.stopDetail.StopDetailFragment
import kotlinx.android.synthetic.main.fragment_stop_location.*
import timber.log.Timber

/**
 *  Page that shows the list of stops of a line.
 */
class StopLocationFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    companion object{
        const val ARG_KEY_STOP = "Stop"
        private const val DefaultZoom = 18f
    }

    private lateinit var stop: Stop
    private lateinit var map: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_stop_location, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        arguments?.takeIf { it.containsKey(ARG_KEY_STOP) }?.apply {
            stop = getParcelable(ARG_KEY_STOP)!!
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = childFragmentManager.findFragmentById(R.id.stop_location_map) as SupportMapFragment
        mapFragment.getMapAsync(this)

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
        map = googleMap
        Timber.d("onMapReady: Map is loaded")
        drawStopsOnMap(map,stop)
    }

    private fun drawStopsOnMap(map: GoogleMap, stop: Stop) {
        val center = stop
        map.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                center.location.toLatLng(),
                DefaultZoom
            )
        )
        addMarker(map, stop)
        map.setOnMarkerClickListener(this)
    }

    private fun addMarker(map: GoogleMap, stop: Stop) {
            val marker = map.addMarker(
                MarkerOptions()
                .position(stop.location.toLatLng())
                .title(stop.name)
                    .)
            marker.tag = stop
    }

    /** Called when the user clicks a marker.  */
    override fun onMarkerClick(marker: Marker): Boolean {

        // Retrieve the data from the marker.
        val stop = marker.tag as Stop
        //navigateToStopDetails(stop)
        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false
    }
}