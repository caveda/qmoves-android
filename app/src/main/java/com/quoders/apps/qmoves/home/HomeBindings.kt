package com.quoders.apps.qmoves.home

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.quoders.apps.qmoves.data.Transport

/**
 * [BindingAdapter]s for the [Transport]s list.
 */
@BindingAdapter("transports")
fun bindRecyclerView(recyclerView: RecyclerView, items: List<Transport>?) {
    items?.let {
        (recyclerView.adapter as TransportsAdapter).submitList(items)
    }
}