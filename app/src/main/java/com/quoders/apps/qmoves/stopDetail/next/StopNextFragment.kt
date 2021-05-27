package com.quoders.apps.qmoves.stopDetail.next

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.quoders.apps.qmoves.R
import com.quoders.apps.qmoves.data.Line
import com.quoders.apps.qmoves.data.Stop
import com.quoders.apps.qmoves.data.source.TransportRepository
import com.quoders.apps.qmoves.databinding.FragmentStopNextBinding
import com.quoders.apps.qmoves.di.BusRealTime
import com.quoders.apps.qmoves.services.realTime.RealTimeService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 *  Page that shows the list of stops of a line.
 */
@AndroidEntryPoint
class StopNextFragment : Fragment() {

    private lateinit var binding: FragmentStopNextBinding

    @BusRealTime
    @Inject lateinit var realTimeService: RealTimeService
    @Inject lateinit var transportRepository: TransportRepository

    companion object{
        const val ARG_KEY_LINE = "Line"
        const val ARG_KEY_STOP = "Stop"
    }

    private lateinit var stop: Stop
    private lateinit var line: Line

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_stop_next,container, false)

        arguments?.takeIf { it.containsKey(ARG_KEY_STOP) && it.containsKey(ARG_KEY_LINE)}?.apply {
            stop = getParcelable(ARG_KEY_STOP)!!
            line = getParcelable(ARG_KEY_LINE)!!
        }

        if (this::stop.isInitialized && this::line.isInitialized) {
            setupViewModel(stop, line)
        }
        return binding.root
    }

    private fun setupViewModel(stop:Stop, line:Line) {

        val viewModelFactory = StopNextViewModelFactory(
            stop, line, transportRepository ,realTimeService)

        val viewModel = ViewModelProvider(this, viewModelFactory).get(
            StopNextViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this.viewLifecycleOwner

        setupArrivalsList(viewModel)
    }

    private fun setupArrivalsList(viewModel: StopNextViewModel) {
        binding.stopNextListView.adapter = StopNextAdapter(viewModel)
        binding.stopNextListView.addItemDecoration(DividerItemDecoration(binding.stopNextListView.context,
            DividerItemDecoration.HORIZONTAL))

        binding.stopNextListView.addItemDecoration(DividerItemDecoration(binding.stopNextListView.context,
            DividerItemDecoration.VERTICAL))

        val gridLayoutManager = GridLayoutManager(activity, 2)
        /*
        gridLayoutManager.spanSizeLookup = object: SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position == 0) 1 else 2
            }
        }*/
        binding.stopNextListView.layoutManager = gridLayoutManager
    }
}