package com.quoders.apps.qmoves.stops

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.quoders.apps.qmoves.R
import com.quoders.apps.qmoves.data.Line
import com.quoders.apps.qmoves.data.Transport
import com.quoders.apps.qmoves.route.RouteFragment

class StopsHostFragment : Fragment() {
    private lateinit var stopsHostAdapter: StopsHostAdapter
    private lateinit var viewPager: ViewPager2
    private val args: StopsHostFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_stops_host, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        stopsHostAdapter = StopsHostAdapter(this, args.line, args.transport)
        viewPager = view.findViewById(R.id.stops_viewPager)
        viewPager.adapter = stopsHostAdapter

        val tabLayout = view.findViewById<TabLayout>(R.id.stops_tabLayout)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = "Stops ${(position + 1)}"
        }.attach()
    }
}

class StopsHostAdapter(fragment: Fragment, val line : Line, val transport: Transport) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) createStopListFragment() else createMapFragment()
    }

    private fun createMapFragment(): Fragment {
        val fragment = RouteFragment()
        fragment.arguments = Bundle().apply {
            putParcelableArray("route", line.stops.map { s -> s.location}.toTypedArray())
            putParcelable("transport", transport)
        }
        return fragment
    }

    private fun createStopListFragment(): Fragment {
        val fragment = StopsFragment()
        fragment.arguments = Bundle().apply {
            putParcelable("line", line)
            putParcelable("transport", transport)
        }
        return fragment
    }
}

private const val ARG_OBJECT = "object"
/*

// Instances of this class are fragments representing a single
// object in our collection.
class DemoObjectFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_collection_object, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey(ARG_OBJECT) }?.apply {
            val textView: TextView = view.findViewById(android.R.id.text1)
            textView.text = getInt(ARG_OBJECT).toString()
        }
    }
}*/
