package com.quoders.apps.qmoves.lines

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.quoders.apps.qmoves.data.Line
import com.quoders.apps.qmoves.databinding.LineViewItemBinding

/**
 * This class implements a [RecyclerView] [ListAdapter] which uses Data Binding to present [List]
 * data, including computing diffs between list elements.
 */
class LinesAdapter (private val viewModel: LinesViewModel): ListAdapter<Line, LinesAdapter.LineViewHolder>(DiffCallback) {

    /**
     * The LineViewHolder constructor takes the binding variable from the associated
     * ViewItem, which gives it access to the full [Line] information.
     */
    class LineViewHolder(private var binding: LineViewItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(line: Line, viewModel: LinesViewModel) {
            binding.line = line
            binding.viewModel = viewModel
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [MarsProperty]
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<Line>() {
        override fun areItemsTheSame(oldItem: Line, newItem: Line): Boolean {
            return oldItem.uniqueId === newItem.uniqueId
        }

        override fun areContentsTheSame(oldItem: Line, newItem: Line): Boolean {
            return oldItem == newItem
        }
    }

    /**
     * Create new [RecyclerView] item views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): LineViewHolder {
        return LineViewHolder(LineViewItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: LineViewHolder, position: Int) {
        val line = getItem(position)
        holder.bind(line, viewModel)
    }
}