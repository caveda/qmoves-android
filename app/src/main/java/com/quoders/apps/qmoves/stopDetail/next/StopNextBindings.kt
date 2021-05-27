package com.quoders.apps.qmoves.stopDetail.next

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.quoders.apps.qmoves.services.realTime.model.TransportRealTimeArrival

/**
 * [BindingAdapter]s for the [TransportRealTimeArrival]s list.
 */
@BindingAdapter("nextTransports")
fun bindRecyclerView(recyclerView: RecyclerView, items: List<StopNextLineTransport>?) {
    items?.let {
        (recyclerView.adapter as StopNextAdapter).submitList(items)
    }
}