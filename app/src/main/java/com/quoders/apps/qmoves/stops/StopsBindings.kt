package com.quoders.apps.qmoves.stops

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
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