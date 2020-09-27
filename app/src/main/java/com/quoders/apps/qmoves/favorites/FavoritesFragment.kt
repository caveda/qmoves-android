package com.quoders.apps.qmoves.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.snackbar.Snackbar
import com.quoders.apps.qmoves.EventObserver
import com.quoders.apps.qmoves.R
import com.quoders.apps.qmoves.data.source.TransportRepositoryFactory
import com.quoders.apps.qmoves.databinding.FragmentFavoritesBinding
import com.quoders.apps.qmoves.home.HomeFragmentDirections
import com.quoders.apps.qmoves.tools.setupSnackbar
import com.quoders.apps.qmoves.tools.showSnackbar

/**
 *  Page that shows the list of favorites
 */
class FavoritesFragment : Fragment(){

    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var viewModel: FavoritesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_favorites,container, false)

        val application = requireNotNull(this.activity).application
        val viewModelFactory = FavoritesViewModelFactory(TransportRepositoryFactory.getInstance(application))

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
            val action = FavoritesFragmentDirections.actionFavoritesFragmentToStopDetailFragment(it.stop,it.line,it.transport)
            findNavController().navigate(action)
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