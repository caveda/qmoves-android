package com.quoders.apps.qmoves.favorites

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.quoders.apps.qmoves.data.Favorite

/**
 * [BindingAdapter]s for the [Favorite]s list.
 */
@BindingAdapter("favorites")
fun bindRecyclerView(recyclerView: RecyclerView, items: List<Favorite>?) {
    items?.let {
        (recyclerView.adapter as FavoritesAdapter).submitList(items)
    }
}