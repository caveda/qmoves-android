package com.quoders.apps.qmoves.lines

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.quoders.apps.qmoves.R
import com.quoders.apps.qmoves.data.Line

/**
 * [BindingAdapter]s for the [Line]s list.
 */
@BindingAdapter("lines")
fun bindRecyclerView(recyclerView: RecyclerView, items: List<Line>?) {
    items?.let {
        (recyclerView.adapter as LinesAdapter).submitList(items)
    }
}


@BindingAdapter("linesLoadingStatus")
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