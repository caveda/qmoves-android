package com.quoders.apps.qmoves.stopDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.quoders.apps.qmoves.R
import com.quoders.apps.qmoves.data.Line
import com.quoders.apps.qmoves.data.Stop
import com.quoders.apps.qmoves.data.Transport
import com.quoders.apps.qmoves.stopDetail.location.StopLocationFragment
import com.quoders.apps.qmoves.stopDetail.next.StopNextFragment
import com.quoders.apps.qmoves.stopDetail.schedule.StopScheduleFragment
import kotlinx.android.synthetic.main.fragment_stop_detail.*

/**
 *  Fragment that hosts the details of the stop: next transport, schedule, location.
 */
class StopDetailFragment : Fragment() {

    companion object{
        const val ARG_KEY_TRANSPORT = "Transport"
        const val ARG_KEY_LINE = "Line"
        const val ARG_KEY_STOP = "Stop"
    }

    private val KEY_CURRENT_VIEW: String = "STOP_DETAIL_CURRENT_VIEW"
    private var stopLocationFragment : Fragment? = null
    private var stopNextFragment: Fragment? = null
    private var stopScheduleFragment: Fragment? = null
    private var currentVisibleViewId : Int = R.id.stopNextFragment
    private lateinit var line: Line
    private lateinit var transport: Transport
    private lateinit var stop: Stop

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_stop_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        arguments?.takeIf { it.containsKey(ARG_KEY_TRANSPORT) && it.containsKey(ARG_KEY_LINE) && it.containsKey(ARG_KEY_STOP) }?.apply {
            line = getParcelable(ARG_KEY_LINE)!!
            transport = getParcelable(ARG_KEY_TRANSPORT)!!
            stop = getParcelable(ARG_KEY_STOP)!!
        }

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
            stopNextFragment =
                StopNextFragment()
            stopNextFragment?.arguments = bundleOf(StopNextFragment.ARG_KEY_STOP to stop)
/*            stopLocationFragment?.arguments = Bundle().apply {
                putParcelable("line", args.line)
                putParcelable("transport", args.transport)
            }*/
        }
        return stopNextFragment as Fragment
    }

    private fun createStopScheduleFragment(): Fragment {
        if (stopScheduleFragment == null) {
            stopScheduleFragment =
                StopScheduleFragment()
            stopScheduleFragment?.arguments = bundleOf(StopScheduleFragment.ARG_KEY_STOP to stop)
/*            stopLocationFragment?.arguments = Bundle().apply {
                putParcelable("line", args.line)
                putParcelable("transport", args.transport)
            }*/
        }
        return stopScheduleFragment as Fragment
    }

    private fun createStopLocationFragment(): Fragment {
        if (stopLocationFragment == null) {
            stopLocationFragment =
                StopLocationFragment()
            stopLocationFragment?.arguments = bundleOf(StopLocationFragment.ARG_KEY_STOP to stop)
/*            stopLocationFragment?.arguments = Bundle().apply {
                putParcelable("line", args.line)
                putParcelable("transport", args.transport)
            }*/
        }
        return stopLocationFragment as Fragment
    }
}