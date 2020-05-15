package com.quoders.apps.qmoves.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.quoders.apps.qmoves.data.Transport
import com.quoders.apps.qmoves.databinding.TransportViewItemBinding

/**
 * This class implements a [RecyclerView] [ListAdapter] which uses Data Binding to present [Transport]
 * data, including computing diffs between list elements.
 */
class TransportsAdapter (private val viewModel: HomeViewModel): ListAdapter<Transport, TransportsAdapter.TransportViewHolder>(DiffCallback) {

    /**
     * The TransportViewHolder constructor takes the binding variable from the associated
     * ViewItem, which gives it access to the full [Transport] information.
     */
    class TransportViewHolder(private var binding: TransportViewItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(transport: Transport, viewModel: HomeViewModel) {
            binding.transport = transport
            binding.viewModel = viewModel
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [Transport]
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<Transport>() {
        override fun areItemsTheSame(oldItem: Transport, newItem: Transport): Boolean {
            return oldItem.name === newItem.name
        }

        override fun areContentsTheSame(oldItem: Transport, newItem: Transport): Boolean {
            return oldItem == newItem
        }
    }

    /**
     * Create new [RecyclerView] item views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): TransportViewHolder {
        return TransportViewHolder(TransportViewItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: TransportViewHolder, position: Int) {
        val transport = getItem(position)
        holder.bind(transport, viewModel)
    }
}