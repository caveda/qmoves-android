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
 *  Fragment that hosts the details of the stop: next transport, schedule, location.
 */
class StopDetailFragment : Fragment() {

    private val KEY_CURRENT_VIEW: String = "STOP_DETAIL_CURRENT_VIEW"
    private var stopLocationFragment : Fragment? = null
    private var stopNextFragment: Fragment? = null
    private var stopScheduleFragment: Fragment? = null
    private var currentVisibleViewId : Int = R.id.stopNextFragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_stop_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupBottomNavigationBar(view)
        if (savedInstanceState != null) {
            currentVisibleViewId = savedInstanceState.getInt(KEY_CURRENT_VIEW,R.id.stopNextFragment)
        }
        showViewFragment(currentVisibleViewId)
    }

    fun setupBottomNavigationBar(view: View) {
        (stop_details_bottomnav_view as BottomNavigationView).setOnNavigationItemSelectedListener { menuItem ->
            currentVisibleViewId = menuItem.itemId
            showViewFragment(currentVisibleViewId)
        }
    }

    private fun showViewFragment(viewId: Int): Boolean {
        return when (viewId) {
            R.id.stopNextFragment -> {
                val fragment = createStopNextFragment()
                openFragment(fragment)
                true
            }
            R.id.stopScheduleFragment -> {
                val fragment = createStopScheduleFragment()
                openFragment(fragment)
                true
            }
            R.id.stopLocationFragment -> {
                val fragment = createStopLocationFragment()
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
        transaction.replace(R.id.stop_details_container, fragment)
        transaction.commit()
    }

    private fun createStopNextFragment(): Fragment {
        if (stopNextFragment == null) {
            stopNextFragment = StopNextFragment()
/*            stopLocationFragment?.arguments = Bundle().apply {
                putParcelable("line", args.line)
                putParcelable("transport", args.transport)
            }*/
        }
        return stopNextFragment as Fragment
    }

    private fun createStopScheduleFragment(): Fragment {
        if (stopScheduleFragment == null) {
            stopScheduleFragment = StopScheduleFragment()
/*            stopLocationFragment?.arguments = Bundle().apply {
                putParcelable("line", args.line)
                putParcelable("transport", args.transport)
            }*/
        }
        return stopScheduleFragment as Fragment
    }

    private fun createStopLocationFragment(): Fragment {
        if (stopLocationFragment == null) {
            stopLocationFragment = StopLocationFragment()
/*            stopLocationFragment?.arguments = Bundle().apply {
                putParcelable("line", args.line)
                putParcelable("transport", args.transport)
            }*/
        }
        return stopLocationFragment as Fragment
    }
}