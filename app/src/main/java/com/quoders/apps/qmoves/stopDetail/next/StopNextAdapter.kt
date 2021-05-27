package com.quoders.apps.qmoves.stopDetail.next

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.quoders.apps.qmoves.databinding.ArrivalViewItemBinding


/**
 * This class implements a [RecyclerView] [ListAdapter] which uses Data Binding to present [StopNextLineTransport]
 * data, including computing diffs between list elements.
 */
class StopNextAdapter (private val viewModel: StopNextViewModel): ListAdapter<StopNextLineTransport, StopNextAdapter.StopNextViewHolder>(DiffCallback) {

    /**
     * The [StopNextViewHolder] constructor takes the binding variable from the associated
     * ViewItem, which gives it access to the full [StopNextLineTransport] information.
     */
    class StopNextViewHolder(private var binding: ArrivalViewItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(nextTransports: StopNextLineTransport) {
            binding.nextTransport = nextTransports
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [StopNextLineTransport]
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<StopNextLineTransport>() {
        override fun areItemsTheSame(oldItem: StopNextLineTransport, newItem: StopNextLineTransport): Boolean {
            return oldItem.lineId === newItem.lineId
        }

        override fun areContentsTheSame(oldItem: StopNextLineTransport, newItem: StopNextLineTransport): Boolean {
            return oldItem == newItem
        }
    }

    /**
     * Create new [RecyclerView] item views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): StopNextViewHolder {
        return StopNextViewHolder(ArrivalViewItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: StopNextViewHolder, position: Int) {
        val nextTransport = getItem(position)
        holder.bind(nextTransport)
    }
}