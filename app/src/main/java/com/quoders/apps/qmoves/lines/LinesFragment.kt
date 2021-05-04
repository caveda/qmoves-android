package com.quoders.apps.qmoves.lines

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
import com.quoders.apps.qmoves.data.source.TransportRepository
import com.quoders.apps.qmoves.databinding.FragmentLinesBinding
import com.quoders.apps.qmoves.tools.setupSnackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 *  List of lines page
 */
@AndroidEntryPoint
class LinesFragment : Fragment(){

    @Inject
    lateinit var transportRepository: TransportRepository

    private lateinit var binding: FragmentLinesBinding
    private lateinit var viewModel: LinesViewModel
    private val args: LinesFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_lines,container, false)

        val viewModelFactory = LinesViewModelFactory(args.transport,transportRepository)

        viewModel = ViewModelProvider(this, viewModelFactory).get(LinesViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this.viewLifecycleOwner

        setupNavigation()
        setupLineList()
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        setupSnackbar()
    }

    private fun setupNavigation() {
        viewModel.eventNavigateStops.observe(viewLifecycleOwner, EventObserver {
            val action = LinesFragmentDirections.actionLinesFragmentToStopsHostFragment(it,args.transport)
            findNavController().navigate(action)
        })
    }

    private fun setupSnackbar() {
        view?.setupSnackbar(viewLifecycleOwner, viewModel.snackbarText, Snackbar.LENGTH_SHORT)
    }

    private fun setupLineList() {
        binding.linesListView.adapter = LinesAdapter(viewModel)
        binding.linesListView.addItemDecoration(DividerItemDecoration(binding.linesListView.context,
            DividerItemDecoration.VERTICAL))
    }
}