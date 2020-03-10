package com.quoders.apps.qmoves.stops

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.quoders.apps.qmoves.data.Stop
import com.quoders.apps.qmoves.databinding.StopViewItemBinding
import com.quoders.apps.qmoves.stops.StopsViewModel

/**
 * This class implements a [RecyclerView] [ListAdapter] which uses Data Binding to present [Stop]
 * data, including computing diffs between list elements.
 */
class StopsAdapter (private val viewModel: StopsViewModel): ListAdapter<Stop, StopsAdapter.StopViewHolder>(DiffCallback) {

    /**
     * The StopViewHolder constructor takes the binding variable from the associated
     * ViewItem, which gives it access to the full [Stop] information.
     */
    class StopViewHolder(private var binding: StopViewItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(stop: Stop, viewModel: StopsViewModel) {
            binding.stop = stop
            binding.viewModel = viewModel
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [Stop]
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<Stop>() {
        override fun areItemsTheSame(oldItem: Stop, newItem: Stop): Boolean {
            return oldItem.id === newItem.id
        }

        override fun areContentsTheSame(oldItem: Stop, newItem: Stop): Boolean {
            return oldItem == newItem
        }
    }

    /**
     * Create new [RecyclerView] item views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): StopViewHolder {
        return StopViewHolder(StopViewItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: StopViewHolder, position: Int) {
        val stop = getItem(position)
        holder.bind(stop, viewModel)
    }
}