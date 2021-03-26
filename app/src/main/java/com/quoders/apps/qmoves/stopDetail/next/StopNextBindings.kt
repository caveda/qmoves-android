package com.quoders.apps.qmoves.stopDetail.next

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.quoders.apps.qmoves.data.Transport
import com.quoders.apps.qmoves.home.TransportsAdapter
import com.quoders.apps.qmoves.realTime.model.StopNextTransport

/**
 * [BindingAdapter]s for the [StopNextTransport]s list.
 */
@BindingAdapter("nextTransports")
fun bindRecyclerView(recyclerView: RecyclerView, items: List<StopNextTransport>?) {
    items?.let {
        (recyclerView.adapter as StopNextAdapter).submitList(items)
    }
}