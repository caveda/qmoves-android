package com.quoders.apps.qmoves.stops

import android.app.Activity
import android.os.Bundle
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.*
import androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavArgument
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.quoders.apps.qmoves.BuildConfig
import com.quoders.apps.qmoves.EventObserver
import com.quoders.apps.qmoves.R
import com.quoders.apps.qmoves.data.Line
import com.quoders.apps.qmoves.data.Transport
import com.quoders.apps.qmoves.data.source.TransportRepositoryFactory
import com.quoders.apps.qmoves.databinding.FragmentStopsBinding
import com.quoders.apps.qmoves.route.RouteFragment
import com.quoders.apps.qmoves.stopDetail.StopDetailPagerAdapter
import com.quoders.apps.qmoves.tools.setupSnackbar
import kotlinx.android.synthetic.main.fragment_stop_detail.*
import kotlinx.android.synthetic.main.fragment_stops_host.*


/**
 *  Fragment that hosts the stops pages: list, map.
 */
class StopsHostFragment : Fragment() {

    private val args: StopsHostFragmentArgs by navArgs()

    // When requested, this adapter returns a DemoObjectFragment,
    // representing an object in the collection.
    private lateinit var viewPager: ViewPager
    private val fragments: MutableList<Pair<Int, Fragment>> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_stops_host, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewPager = view.findViewById(R.id.stops_host_pager)
        fragments.add(Pair(R.string.stops_list,createStopsFragment()))
        fragments.add(Pair(R.string.stops_map,createRouteMapFragment()))


        // The pager adapter, which provides the pages to the view pager widget.
        val pagerAdapter = StopsPagerAdapter(this.childFragmentManager)
        pagerAdapter.addFragments(fragments.toTypedArray());
        viewPager.adapter = pagerAdapter

        val tabLayout = view.findViewById(R.id.stops_host_tab_layout) as TabLayout
        tabLayout.setupWithViewPager(viewPager)
    }

    private fun createStopsFragment(): Fragment {
        val fragment = StopsFragment()
        fragment.arguments = Bundle().apply {
            putParcelable(StopsFragment.ARG_KEY_LINE, args.line)
            putParcelable(StopsFragment.ARG_KEY_TRANSPORT, args.transport)
        }
        return fragment
    }

    private fun createRouteMapFragment(): Fragment {
        val fragment = RouteFragment()
        fragment.arguments = Bundle().apply {
            putParcelable(StopsFragment.ARG_KEY_LINE, args.line)
            putParcelable(StopsFragment.ARG_KEY_TRANSPORT, args.transport)
        }
        return fragment
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private inner class StopsPagerAdapter(fm: FragmentManager) :
        FragmentStatePagerAdapter(fm) {

        private val fragments = mutableListOf<Pair<Int, Fragment>>()

        fun addFragments(fragment: Array<Pair<Int, Fragment>>) {
            fragments.addAll(fragment)
        }

        override fun getPageTitle(position: Int): CharSequence {
            return getString(fragments[position].first)
        }

        override fun getCount(): Int = fragments.size

        override fun getItem(position: Int): Fragment = fragments[position].second
    }
}
