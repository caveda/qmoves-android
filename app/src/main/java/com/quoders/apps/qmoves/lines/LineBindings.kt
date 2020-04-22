package com.quoders.apps.qmoves.lines

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
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