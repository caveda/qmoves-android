package com.quoders.apps.qmoves.stops

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.quoders.apps.qmoves.R
import com.quoders.apps.qmoves.route.RouteFragment
import kotlinx.android.synthetic.main.fragment_stops_host.*

/**
 *  Fragment that hosts the stops pages: list, map.
 */
class StopsHostFragment : Fragment() {
    private val KEY_CURRENT_VIEW: String = "STOPS_HOST_CURRENT_VIEW"
    private val args: StopsHostFragmentArgs by navArgs()
    private var mapFragment : Fragment? = null
    private var stopListFragment: Fragment? = null
    private var currentVisibleViewId : Int = R.id.stopsFragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_stops_host, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupBottomNavigationBar(view)
        if (savedInstanceState != null) {
            currentVisibleViewId = savedInstanceState.getInt(KEY_CURRENT_VIEW,R.id.stopsFragment)
        }
        showViewFragment(currentVisibleViewId)
    }

    fun setupBottomNavigationBar(view: View) {
        val navViewController = findNavController()
        stops_bottom_view?.setupWithNavController(navViewController)
        stops_bottom_view.setOnNavigationItemSelectedListener { menuItem ->
            findNavController().navigate(menuItem.itemId,getDestFragmentArguments())
            true
        }
    }

    private fun showViewFragment(viewId: Int): Boolean {
        return when (viewId) {
            R.id.stopsFragment -> {
                val fragment = createStopListFragment()
                openFragment(fragment)
                true
            }
            R.id.routeFragment -> {
                val fragment = createMapFragment()
                openFragment(fragment)
                true
            }
            else -> false
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(KEY_CURRENT_VIEW, currentVisibleViewId)
        super.onSaveInstanceState(outState)
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = childFragmentManager.beginTransaction()
        transaction.replace(R.id.main_container, fragment)
        transaction.commit()
    }

    private fun createMapFragment(): Fragment {
        if (mapFragment == null) {
            mapFragment = RouteFragment()
            mapFragment?.arguments = getDestFragmentArguments()
        }
        return mapFragment as Fragment
    }

    private fun createStopListFragment(): Fragment {
        if (stopListFragment == null) {
            stopListFragment = StopsFragment()
            stopListFragment?.arguments = getDestFragmentArguments()
        }
        return stopListFragment as Fragment
    }

    private fun getDestFragmentArguments(): Bundle {
        return Bundle().apply {
            putParcelable("line", args.line)
            putParcelable("transport", args.transport)
        }
    }
}
