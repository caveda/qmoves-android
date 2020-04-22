package com.quoders.apps.qmoves.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.quoders.apps.qmoves.EventObserver
import com.quoders.apps.qmoves.R
import com.quoders.apps.qmoves.data.Transport
import com.quoders.apps.qmoves.data.source.remote.FirebaseClientConfig
import com.quoders.apps.qmoves.databinding.FragmentHomeBinding
import com.quoders.apps.qmoves.tools.setupSnackbar

/**
 * Limes
 */
class HomeFragment : Fragment(){

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_home,container, false)

        viewModel = ViewModelProvider(this, HomeViewModelFactory(buildFirebaseConfig())).
            get(HomeViewModel::class.java)
        binding.homeViewModel = viewModel
        binding.lifecycleOwner = this.viewLifecycleOwner

        setupNavigation()

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        // Initializations that requires a created view.
        setupSnackbar()
    }


    private fun buildFirebaseConfig() =
        FirebaseClientConfig(
            funcUrl = getString(R.string.firebase_func_url),
            funcHeaderValue = getString(R.string.firebase_func_header_value),
            storageMetadataPath = getString(R.string.firebase_storage_metadata),
            storageDataPath = getString(R.string.firebase_storage_data)
        )

    private fun setupSnackbar() {
        view?.setupSnackbar(viewLifecycleOwner, viewModel.snackbarText, Snackbar.LENGTH_SHORT)
    }

    private fun setupNavigation() {
        viewModel.eventNavigateLines.observe(viewLifecycleOwner, EventObserver {
            navigateToLines(it)
        })
    }

    private fun navigateToLines(transport: Transport) {
        val action = HomeFragmentDirections.actionHomeFragmentToLinesFragment(transport)
        findNavController().navigate(action)
    }
}