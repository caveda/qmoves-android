package com.quoders.apps.qmoves.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.quoders.apps.qmoves.EventObserver
import com.quoders.apps.qmoves.R
import com.quoders.apps.qmoves.data.Transport
import com.quoders.apps.qmoves.data.source.TransportRepository
import com.quoders.apps.qmoves.databinding.FragmentHomeBinding
import com.quoders.apps.qmoves.tools.setupSnackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Home page of the app
 */
@AndroidEntryPoint
class HomeFragment : Fragment(){

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel

    @Inject
    lateinit var transportRepository: TransportRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_home,container, false)

        viewModel = ViewModelProvider(this, HomeViewModelFactory(transportRepository)).
            get(HomeViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this.viewLifecycleOwner

        setupNavigation()
        setupTransportsList()
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        // Initializations that requires a created view.
        setupSnackbar()
    }

    private fun setupSnackbar() {
        view?.setupSnackbar(viewLifecycleOwner, viewModel.snackbarText, Snackbar.LENGTH_SHORT)
    }

    private fun setupNavigation() {
        viewModel.eventNavigateLines.observe(viewLifecycleOwner, EventObserver {
            navigateToLines(it)
        })

        viewModel.eventNavigateFavorites.observe(viewLifecycleOwner, EventObserver {
            navigateToFavorites()
        })
    }

    private fun navigateToLines(transport: Transport) {
        val action = HomeFragmentDirections.actionHomeFragmentToLinesFragment(transport)
        findNavController().navigate(action)
    }

    private fun navigateToFavorites() {
        val action = HomeFragmentDirections.actionHomeFragmentToFavoritesFragment()
        findNavController().navigate(action)
    }

    private fun setupTransportsList() {
        binding.transportsListView.layoutManager = GridLayoutManager(activity, 2)
        binding.transportsListView.adapter = TransportsAdapter(viewModel)
    }
}