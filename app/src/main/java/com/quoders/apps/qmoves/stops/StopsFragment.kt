package com.quoders.apps.qmoves.stops

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.snackbar.Snackbar
import com.quoders.apps.qmoves.EventObserver
import com.quoders.apps.qmoves.R
import com.quoders.apps.qmoves.data.source.TransportRepositoryFactory
import com.quoders.apps.qmoves.databinding.FragmentStopsBinding
import com.quoders.apps.qmoves.tools.setupSnackbar
import com.quoders.apps.qmoves.tools.showSnackbar

/**
 *  Page that shows the list of stops of a line.
 */
class StopsFragment : Fragment(){

    private lateinit var binding: FragmentStopsBinding
    private lateinit var viewModel: StopsViewModel
    private val args: StopsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_stops,container, false)

        val application = requireNotNull(this.activity).application
        val viewModelFactory = StopsViewModelFactory(args.transport, args.line,
            TransportRepositoryFactory.getInstance(application))

        viewModel = ViewModelProvider(this, viewModelFactory).get(
            StopsViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this.viewLifecycleOwner

        setupNavigation()
        setupStopList()
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        setupSnackbar()
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
}