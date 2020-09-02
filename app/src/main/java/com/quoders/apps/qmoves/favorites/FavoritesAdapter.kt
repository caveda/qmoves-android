package com.quoders.apps.qmoves.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.quoders.apps.qmoves.data.Favorite
import com.quoders.apps.qmoves.databinding.FavoriteViewItemBinding

/**
 * This class implements a [RecyclerView] [ListAdapter] which uses Data Binding to present [Favorite]
 * data, including computing diffs between list elements.
 */
class FavoritesAdapter (private val viewModel: FavoritesViewModel): ListAdapter<Favorite, FavoritesAdapter.FavoriteViewHolder>(DiffCallback) {

    /**
     * The StopViewHolder constructor takes the binding variable from the associated
     * ViewItem, which gives it access to the full [Favorite] information.
     */
    class FavoriteViewHolder(private var binding: FavoriteViewItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(favorite: Favorite, viewModel: FavoritesViewModel) {
            binding.favorite = favorite
            binding.viewModel = viewModel
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [Favorite]
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<Favorite>() {
        override fun areItemsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
            return oldItem.transport.name === newItem.transport.name &&
                    oldItem.line.agencyId === newItem.line.agencyId &&
                    oldItem.stop.code === newItem.stop.code
        }

        override fun areContentsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
            return oldItem == newItem
        }
    }

    /**
     * Create new [RecyclerView] item views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(FavoriteViewItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val favorite = getItem(position)
        holder.bind(favorite, viewModel)
    }
}