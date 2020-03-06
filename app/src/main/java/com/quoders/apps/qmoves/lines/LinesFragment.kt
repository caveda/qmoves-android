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
import com.quoders.apps.qmoves.databinding.FragmentLinesBinding
import com.quoders.apps.qmoves.home.HomeViewModel
import com.quoders.apps.qmoves.tools.setupSnackbar
import com.quoders.apps.qmoves.tools.showSnackbar

/**
 *  List of lines page
 */
class LinesFragment : Fragment(){

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


        viewModel = ViewModelProvider(this, LinesViewModelFactory(args.transport)).get(LinesViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this.viewLifecycleOwner

        setupNavigation()
        setupLineList()
        setupSnackbar()
        return binding.root
    }

    private fun setupNavigation() {
        viewModel.eventNavigateStops.observe(viewLifecycleOwner, EventObserver {
            // TODO Implement real navigation
            view?.showSnackbar("Navigating to stops of ${it.name}",Snackbar.LENGTH_SHORT)
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