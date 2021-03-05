package com.quoders.apps.qmoves.stopDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayoutMediator
import com.quoders.apps.qmoves.R
import com.quoders.apps.qmoves.route.RouteFragment
import com.quoders.apps.qmoves.stops.StopsFragment
import kotlinx.android.synthetic.main.fragment_stop_detail.*
import kotlinx.android.synthetic.main.fragment_stops_host.*

/**
 *  Fragment that hosts the stops pages: list, map.
 */
class StopDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_stop_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    }
}