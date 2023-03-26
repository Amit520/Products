package com.app.productapp.utils

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.app.productapp.R
import com.app.productapp.databinding.BottomSheetModalBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MyBottomSheetDialogFragment(private var mListener: OptionClickListener) :
    BottomSheetDialogFragment(R.layout.bottom_sheet_modal) {

    private lateinit var binding: BottomSheetModalBinding

    interface OptionClickListener {
        fun onOptionClick(selectedOption: Constants.MEDIA_OPTIONS)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
//        mListener = context as OptionClickListener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = BottomSheetModalBinding.bind(view)
        val options = listOf("Camera", "Gallery")
        binding.listViewOptions.adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, options)
        binding.listViewOptions.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, childView, position, id ->
                this@MyBottomSheetDialogFragment.dismiss()
                when (position) {
                    0 -> mListener.onOptionClick(Constants.MEDIA_OPTIONS.CAMERA)
                    1 -> mListener.onOptionClick(Constants.MEDIA_OPTIONS.GALLERY)
                }
            }
    }

}