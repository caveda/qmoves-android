package com.quoders.apps.qmoves.lines

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.quoders.apps.qmoves.R
import com.quoders.apps.qmoves.databinding.FragmentLinesBinding
import com.quoders.apps.qmoves.home.HomeViewModel

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
        binding.linesViewModel = viewModel
        binding.lifecycleOwner = this

        return binding?.root
    }
}