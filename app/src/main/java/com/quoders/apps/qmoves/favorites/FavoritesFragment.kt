package com.quoders.apps.qmoves.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.snackbar.Snackbar
import com.quoders.apps.qmoves.EventObserver
import com.quoders.apps.qmoves.R
import com.quoders.apps.qmoves.data.source.TransportRepository
import com.quoders.apps.qmoves.databinding.FragmentFavoritesBinding
import com.quoders.apps.qmoves.stopDetail.StopDetailFragment
import com.quoders.apps.qmoves.tools.setupSnackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 *  Page that shows the list of favorites
 */
@AndroidEntryPoint
class FavoritesFragment : Fragment(){

    @Inject
    lateinit var transportRepository: TransportRepository

    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var viewModel: FavoritesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_favorites,container, false)

        val viewModelFactory = FavoritesViewModelFactory(transportRepository)

        viewModel = ViewModelProvider(this, viewModelFactory).get(
            FavoritesViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this.viewLifecycleOwner

        setupNavigation()
        setupFavoriteList()
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        setupSnackbar()
    }

    private fun setupNavigation() {
        viewModel.eventNavigateToStopDetail.observe(viewLifecycleOwner, EventObserver {
            val bundle = bundleOf(
                StopDetailFragment.ARG_KEY_TRANSPORT to it.transport,
                StopDetailFragment.ARG_KEY_LINE to it.line,
                StopDetailFragment.ARG_KEY_STOP to it.stop)
            findNavController().navigate(R.id.action_global_stopDetailFragment, bundle)
        })
    }

    private fun setupSnackbar() {
        view?.setupSnackbar(viewLifecycleOwner, viewModel.snackbarText, Snackbar.LENGTH_SHORT)
    }

    private fun setupFavoriteList() {
        binding.favoritesListView.adapter = FavoritesAdapter(viewModel)

        binding.favoritesListView.addItemDecoration(DividerItemDecoration(binding.favoritesListView.context,
            DividerItemDecoration.VERTICAL))
    }
}