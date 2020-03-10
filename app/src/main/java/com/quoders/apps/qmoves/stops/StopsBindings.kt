package com.quoders.apps.qmoves.stops

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.quoders.apps.qmoves.R
import com.quoders.apps.qmoves.data.Line
import com.quoders.apps.qmoves.data.Stop

/**
 * [BindingAdapter]s for the [Stop]s list.
 */
@BindingAdapter("stops")
fun bindRecyclerView(recyclerView: RecyclerView, items: List<Stop>?) {
    items?.let {
        (recyclerView.adapter as StopsAdapter).submitList(items)
    }
}


@BindingAdapter("stopsLoadingStatus")
fun bindStatus(statusImageView: ImageView, status: DataLoadingStatus?) {
    status?.let{
        when (status) {
            DataLoadingStatus.LOADING -> {
                statusImageView.visibility = View.VISIBLE
                statusImageView.setImageResource(R.drawable.animation_loading)
            }
            DataLoadingStatus.ERROR -> {
                statusImageView.visibility = View.VISIBLE
                statusImageView.setImageResource(R.drawable.ic_data_fetching_error)
            }
            DataLoadingStatus.DONE -> {
                statusImageView.visibility = View.GONE
            }
        }
    }
}