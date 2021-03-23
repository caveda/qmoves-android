package com.quoders.apps.qmoves.stopDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.snackbar.Snackbar
import com.quoders.apps.qmoves.EventObserver
import com.quoders.apps.qmoves.R
import com.quoders.apps.qmoves.data.Line
import com.quoders.apps.qmoves.data.Stop
import com.quoders.apps.qmoves.data.Transport
import com.quoders.apps.qmoves.data.source.TransportRepositoryFactory
import com.quoders.apps.qmoves.databinding.FragmentStopsBinding
import com.quoders.apps.qmoves.favorites.FavoritesFragmentDirections
import com.quoders.apps.qmoves.lines.LinesFragmentDirections
import com.quoders.apps.qmoves.realTime.BusRealTimeService
import com.quoders.apps.qmoves.realTime.RealTimeService
import com.quoders.apps.qmoves.tools.setupSnackbar
import com.quoders.apps.qmoves.tools.showSnackbar
import kotlinx.android.synthetic.main.fragment_stop_location.*
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
        realtimeService.getStopNextTransports(stop)
    }
}