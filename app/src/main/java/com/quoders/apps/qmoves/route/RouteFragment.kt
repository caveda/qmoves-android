package com.quoders.apps.qmoves.route

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.material.snackbar.Snackbar
import com.quoders.apps.qmoves.EventObserver
import com.quoders.apps.qmoves.R
import com.quoders.apps.qmoves.databinding.FragmentStopsBinding
import com.quoders.apps.qmoves.lines.LinesFragmentArgs
import com.quoders.apps.qmoves.stops.StopsAdapter
import com.quoders.apps.qmoves.stops.StopsFragmentArgs
import com.quoders.apps.qmoves.stops.StopsViewModel
import com.quoders.apps.qmoves.stops.StopsViewModelFactory
import com.quoders.apps.qmoves.tools.setupSnackbar
import com.quoders.apps.qmoves.tools.showSnackbar

/**
 *  Page that shows the list of stops of a line.
 */
class RouteFragment : Fragment(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private lateinit var viewModel: RouteViewModel
    //private val args: RouteFragmentArgs by navArgs()

/*
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_stops,container, false)


        viewModel = ViewModelProvider(this, StopsViewModelFactory(args.transport, args.line)).get(
            StopsViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this.viewLifecycleOwner

        setupNavigation()
        setupStopList()
        setupSnackbar()
        return binding.root
    }

    private fun setupNavigation() {
        viewModel.eventNavigateToStopDetail.observe(viewLifecycleOwner, EventObserver {
            // TODO Implement real navigation
            view?.showSnackbar("Navigating to stop detail of ${it.name}",Snackbar.LENGTH_SHORT)
        })
    }

    private fun setupSnackbar() {
        view?.setupSnackbar(viewLifecycleOwner, viewModel.snackbarText, Snackbar.LENGTH_SHORT)
    }

    private fun setupStopList() {
        binding.stopsListView.adapter = StopsAdapter(viewModel)

        binding.stopsListView.addItemDecoration(DividerItemDecoration(binding.stopsListView.context,
            DividerItemDecoration.VERTICAL))
    }
*/

    override fun onMapReady(p0: GoogleMap?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}