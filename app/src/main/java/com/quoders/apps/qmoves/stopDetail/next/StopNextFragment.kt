package com.quoders.apps.qmoves.stopDetail.next

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.quoders.apps.qmoves.R
import com.quoders.apps.qmoves.data.Stop
import com.quoders.apps.qmoves.realTime.BusRealTimeService
import kotlinx.android.synthetic.main.fragment_stop_next.*

/**
 *  Page that shows the list of stops of a line.
 */
class StopNextFragment : Fragment() {

    companion object{
        const val ARG_KEY_STOP = "Stop"
    }

    private lateinit var stop: Stop

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_stop_next, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        arguments?.takeIf { it.containsKey(ARG_KEY_STOP) }?.apply {
            stop = getParcelable(ARG_KEY_STOP)!!
        }

        stop.let {
            stop_next_label.text = "${stop.name} \n ${stop.connections}";
        }

        val realtimeService = BusRealTimeService()
        val viewModelFactory =
            StopNextViewModelFactory(
                stop, realtimeService
            )

        val viewModel = ViewModelProvider(this, viewModelFactory).get(
            StopNextViewModel::class.java)
    }
}