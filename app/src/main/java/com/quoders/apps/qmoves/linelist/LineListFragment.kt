package com.quoders.apps.qmoves.linelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.quoders.apps.qmoves.R
import com.quoders.apps.qmoves.databinding.FragmentLinelistBinding

class LineListFragment : Fragment(){

    private var binding: FragmentLinelistBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_linelist,container, false)

        return binding?.root
    }
}