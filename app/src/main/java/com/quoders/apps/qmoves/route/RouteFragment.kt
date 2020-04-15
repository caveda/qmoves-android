package com.quoders.apps.qmoves.route

import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.quoders.apps.qmoves.R
import com.quoders.apps.qmoves.data.Location
import com.quoders.apps.qmoves.data.Stop
import java.util.*

/**
 *  Page that shows the list of stops of a line.
 */
class RouteFragment : Fragment(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private val args: RouteFragmentArgs by navArgs()

    companion object {
        private const val DefaultZoom = 16f
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_route, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = childFragmentManager.findFragmentById(R.id.route_map) as SupportMapFragment

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

        if (args.stops.isNotEmpty()) {
            val center = args.stops[args.stops.size/2]
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(center.location.toLatLng(), DefaultZoom))
            addMarkers(map, args.stops.asList())
        }

        if (args.route.isNotEmpty()) {
            drawRouteOverlay (map, args.route.asList())
        }
    }

    private fun addMarkers(map: GoogleMap, stops: List<Stop>) {
        for (s in stops) {
            map.addMarker(MarkerOptions().
                position(s.location.toLatLng()).
                title(s.name))
        }
    }

    private fun drawRouteOverlay(map: GoogleMap, route: List<Location>) {
        val mapPoints = route.map{l -> l.toLatLng()}
        map.addPolyline(
            PolylineOptions()
                .addAll(mapPoints)
                .width(5f)
                .color(Color.RED)
        )
    }
}