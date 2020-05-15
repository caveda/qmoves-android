package com.quoders.apps.qmoves

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.quoders.apps.qmoves.data.DataLoadingStatus
import com.quoders.apps.qmoves.data.Transport

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

@BindingAdapter("transportImage")
fun bindStatus(transportImageView: ImageView, type: Transport.TransportType?) {
    when (type) {
        Transport.TransportType.BUS -> {
            transportImageView.setImageResource(R.drawable.ic_transport_bus)
        }
        Transport.TransportType.SUBWAY -> {
            transportImageView.setImageResource(R.drawable.ic_transport_subway)
        }
        Transport.TransportType.TRAM -> {
            transportImageView.setImageResource(R.drawable.ic_transport_tram)
        }
    }
}