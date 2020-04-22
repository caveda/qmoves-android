package com.quoders.apps.qmoves

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.quoders.apps.qmoves.data.DataLoadingStatus

@BindingAdapter("loadingStatus")
fun bindStatus(statusImageView: ImageView, status: DataLoadingStatus?) {
    when (status) {
        DataLoadingStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.animation_loading)
        }
        DataLoadingStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        DataLoadingStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}