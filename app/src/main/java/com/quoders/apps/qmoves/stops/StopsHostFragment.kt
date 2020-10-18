package com.quoders.apps.qmoves.stops

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavArgument
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.quoders.apps.qmoves.BuildConfig
import com.quoders.apps.qmoves.R
import com.quoders.apps.qmoves.route.RouteFragment
import com.quoders.apps.qmoves.stopDetail.StopDetailPagerAdapter
import kotlinx.android.synthetic.main.fragment_stop_detail.*
import kotlinx.android.synthetic.main.fragment_stops_host.*


/**
 *  Fragment that hosts the stops pages: list, map.
 */
class StopsHostFragment : Fragment() {

    private val KEY_CURRENT_VIEW: String = "STOPS_HOST_CURRENT_VIEW"
    private val args: StopsHostFragmentArgs by navArgs()
    private var currentVisibleViewId : Int = R.id.stopsFragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_stops_host, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        stops_host_pager.adapter = activity?.let { StopsHostFragmentAdapter(it, buildFragmentArguments())}

        TabLayoutMediator(stops_host_tablayout, stops_host_pager) { tab, position ->
            tab.text = resources.getString(StopsHostFragmentAdapter.StopsFragments.getTitle(position))
        }.attach()
    }

    private fun buildFragmentArguments(): Bundle {
        return Bundle().apply {
            putParcelable("line", args.line)
            putParcelable("transport", args.transport)
        }
    }
}
