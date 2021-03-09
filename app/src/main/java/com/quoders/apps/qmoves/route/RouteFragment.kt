package com.quoders.apps.qmoves.route

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.quoders.apps.qmoves.R
import com.quoders.apps.qmoves.data.Line
import com.quoders.apps.qmoves.data.Stop
import com.quoders.apps.qmoves.data.Transport
import com.quoders.apps.qmoves.data.source.TransportRepositoryFactory
import com.quoders.apps.qmoves.databinding.FragmentStopsBinding
import com.quoders.apps.qmoves.stopDetail.StopDetailFragment
import com.quoders.apps.qmoves.stops.StopsFragment
import timber.log.Timber

/**
 *  Page that shows the list of stops of a line.
 */
class RouteFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    companion object{
        const val ARG_KEY_TRANSPORT = "Transport"
        const val ARG_KEY_LINE = "Line"
        private const val DefaultZoom = 16f
    }

    private lateinit var map: GoogleMap
    private lateinit var viewModel: RouteViewModel
    private lateinit var line: Line
    private lateinit var transport: Transport

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_route, container, false)
    }

    private fun setupViewModelObservers() {
        viewModel.route.observe(viewLifecycleOwner, Observer { route ->
            if (route.isNotEmpty() && this::map.isInitialized)
                drawRouteOverlay(map, route)
        })

        viewModel.stops.observe(viewLifecycleOwner, Observer { stops ->
            if (stops.isNotEmpty() && this::map.isInitialized)
                drawStopsOnMap(map, stops)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        arguments?.takeIf { it.containsKey(RouteFragment.ARG_KEY_TRANSPORT) && it.containsKey(
            RouteFragment.ARG_KEY_LINE) }?.apply {
            line = getParcelable(RouteFragment.ARG_KEY_LINE)!!
            transport = getParcelable(RouteFragment.ARG_KEY_TRANSPORT)!!
        }

        val application = requireNotNull(this.activity).application
        val viewModelFactory = RouteViewModelFactory(transport, line,
            TransportRepositoryFactory.getInstance(application))

        viewModel = ViewModelProvider(this, viewModelFactory).get(RouteViewModel::class.java)

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
        Timber.d("onMapReady: Map is loaded")

        // Now we can observe the changes in the viewModel data
        setupViewModelObservers()
    }

    private fun drawStopsOnMap(map: GoogleMap, stops: List<Stop>) {
        val center = stops[stops.size / 2]
        map.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                center.location.toLatLng(),
                DefaultZoom
            )
        )
        addMarkers(map, stops)
        map.setOnMarkerClickListener(this)
    }

    private fun addMarkers(map: GoogleMap, stops: List<Stop>) {
        for (s in stops) {
            val marker = map.addMarker(MarkerOptions()
                .position(s.location.toLatLng())
                .title(s.name))
            marker.tag = s
        }
    }

    /** Called when the user clicks a marker.  */
    override fun onMarkerClick(marker: Marker): Boolean {

        // Retrieve the data from the marker.
        val stop = marker.tag as Stop
        navigateToStopDetails(stop)
        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false
    }

    private fun navigateToStopDetails(stop: Stop) {
        val bundle = bundleOf(
            StopDetailFragment.ARG_KEY_TRANSPORT to transport,
            StopDetailFragment.ARG_KEY_LINE to line, StopDetailFragment.ARG_KEY_STOP to stop)
        findNavController().navigate(R.id.action_global_stopDetailFragment, bundle)
    }

    private fun drawRouteOverlay(map: GoogleMap, route: List<LatLng>) {
        map.addPolyline(
            PolylineOptions()
                .addAll(route)
                .width(5f)
                .color(Color.RED)
        )
    }
}