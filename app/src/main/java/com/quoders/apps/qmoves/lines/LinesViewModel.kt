package com.quoders.apps.qmoves.lines

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.quoders.apps.qmoves.data.Transport

/**
 *  ViewModel of Lines page
 */
class LinesViewModel (transport: Transport) : ViewModel() {

    private val _transportName = MutableLiveData<String>()
    val transportName : LiveData<String> = _transportName

    init {
        _transportName.value = transport.name
    }
}