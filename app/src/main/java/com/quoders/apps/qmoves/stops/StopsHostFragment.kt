package com.quoders.apps.qmoves.stops

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
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
    private var currentVisibleViewId : Int = R.id.menu_stop_list

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
            currentVisibleViewId = savedInstanceState.getInt(KEY_CURRENT_VIEW,R.id.menu_stop_list)
        }
        showViewFragment(currentVisibleViewId)
    }

    fun setupBottomNavigationBar(view: View) {
        (stops_bottom_view as BottomNavigationView).setOnNavigationItemSelectedListener { menuItem ->
            currentVisibleViewId = menuItem.itemId
            showViewFragment(currentVisibleViewId)
        }
    }

    private fun showViewFragment(viewId: Int): Boolean {
        return when (viewId) {
            R.id.menu_stop_list -> {
                val fragment = createStopListFragment()
                openFragment(fragment)
                true
            }
            R.id.menu_stop_map -> {
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
            mapFragment?.arguments = Bundle().apply {
                putParcelable("line", args.line)
                putParcelable("transport", args.transport)
            }
        }
        return mapFragment as Fragment
    }

    private fun createStopListFragment(): Fragment {
        if (stopListFragment == null) {
            stopListFragment = StopsFragment()
            stopListFragment?.arguments = Bundle().apply {
                putParcelable("line", args.line)
                putParcelable("transport", args.transport)
            }
        }
        return stopListFragment as Fragment
    }
}
