package com.quoders.apps.qmoves.stopDetail.schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.quoders.apps.qmoves.R
import com.quoders.apps.qmoves.data.Stop
import kotlinx.android.synthetic.main.fragment_stop_schedule.*

/**
 *  Page that shows the list of stops of a line.
 */
class StopScheduleFragment : Fragment() {

    companion object{
        const val ARG_KEY_STOP = "Stop"
    }

    private lateinit var stop: Stop

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_stop_schedule, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        arguments?.takeIf { it.containsKey(ARG_KEY_STOP) }?.apply {
            stop = getParcelable(ARG_KEY_STOP)!!
        }

        stop.let {
            stop_schedule_label.text = stop.schedule.toString();
        }
    }
}