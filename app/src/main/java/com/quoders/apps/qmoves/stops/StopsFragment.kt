package com.quoders.apps.qmoves.stops

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.snackbar.Snackbar
import com.quoders.apps.qmoves.EventObserver
import com.quoders.apps.qmoves.R
import com.quoders.apps.qmoves.data.Line
import com.quoders.apps.qmoves.data.Transport
import com.quoders.apps.qmoves.data.source.TransportRepositoryFactory
import com.quoders.apps.qmoves.databinding.FragmentStopsBinding
import com.quoders.apps.qmoves.favorites.FavoritesFragmentDirections
import com.quoders.apps.qmoves.lines.LinesFragmentDirections
import com.quoders.apps.qmoves.tools.setupSnackbar
import com.quoders.apps.qmoves.tools.showSnackbar

/**
 *  Page that shows the list of stops of a line.
 */
class StopsFragment : Fragment() {

    companion object{
        const val ARG_KEY_TRANSPORT = "Transport"
        const val ARG_KEY_LINE = "Line"
    }

    private lateinit var binding: FragmentStopsBinding
    private lateinit var viewModel: StopsViewModel
    private lateinit var line: Line
    private lateinit var transport: Transport

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_stops,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {

        arguments?.takeIf { it.containsKey(ARG_KEY_TRANSPORT) && it.containsKey(ARG_KEY_LINE) }?.apply {
            line = getParcelable(ARG_KEY_LINE)!!
            transport = getParcelable(ARG_KEY_TRANSPORT)!!
        }

        val application = requireNotNull(this.activity).application
        val viewModelFactory = StopsViewModelFactory(transport, line,
            TransportRepositoryFactory.getInstance(application))

        viewModel = ViewModelProvider(this, viewModelFactory).get(
            StopsViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this.viewLifecycleOwner

        setupNavigation()
        setupStopList()
    }

    override fun onStart() {
        super.onStart()
        setupSnackbar()
    }

    private fun setupNavigation() {
        viewModel.eventNavigateToStopDetail.observe(viewLifecycleOwner, EventObserver {
            val action = StopsFragmentDirections.actionStopsFragmentToStopDetailFragment3(it,line,transport)
            val controller = findNavController()
            controller.navigate(action)
        })
    }

    private fun setupSnackbar() {
        view?.setupSnackbar(viewLifecycleOwner, viewModel.snackbarText, Snackbar.LENGTH_SHORT)
    }

    private fun setupStopList() {
        binding.stopsListView.adapter = StopsAdapter(viewModel)

        binding.stopsListView.addItemDecoration(
            DividerItemDecoration(binding.stopsListView.context,
                DividerItemDecoration.VERTICAL)
        )
    }
}