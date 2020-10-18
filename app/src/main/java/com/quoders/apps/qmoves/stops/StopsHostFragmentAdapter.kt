package com.quoders.apps.qmoves.stops

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.quoders.apps.qmoves.R
import com.quoders.apps.qmoves.route.RouteFragment

class StopsHostFragmentAdapter(activity: FragmentActivity, val args: Bundle) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = StopsFragments.size()

    override fun createFragment(position: Int): Fragment {
        return StopsFragments.getFragment(position, args)
    }

    /**
     * Builds the necessary fragment according to the position.
     */
    object StopsFragments {

        private val fragmentBuilders: List<Pair<Int, (Bundle) -> Fragment>>

        init {
            fragmentBuilders = listOf(
                Pair(R.string.stops_list, { args -> createStopListFragment(args) }),
                Pair(R.string.stops_map, { args -> createMapFragment(args) })
            )
        }

        fun size(): Int {
            return fragmentBuilders.size
        }

        fun getFragment(position: Int, args: Bundle): Fragment {
            require(position < fragmentBuilders.size)
            return fragmentBuilders[position].second(args)
        }

        fun getTitle(position: Int): Int {
            require(position < fragmentBuilders.size)
            return fragmentBuilders[position].first
        }

        private fun createMapFragment(args: Bundle): Fragment {
            val mapFragment = RouteFragment()
            mapFragment.arguments = args
            return mapFragment
        }

        private fun createStopListFragment(args: Bundle): Fragment {
            val stopListFragment = StopsFragment()
            stopListFragment.arguments = args
            return stopListFragment as Fragment
        }
    }
}
