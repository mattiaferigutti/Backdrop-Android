package com.mattiaferigutti.backdrop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mattiaferigutti.backdrop.databinding.FragmentWindowBinding


class WindowFragment : BottomSheetDialogFragment(), OnBottomSheetCallbacks {
    private var _binding: FragmentWindowBinding? = null
    // This property is only valid between `onCreateView` and `onDestroyView`
    private val binding get() = _binding!!

    private var currentState: Int = BottomSheetBehavior.STATE_EXPANDED

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        (activity as MainActivity).setOnBottomSheetCallbacks(this)

        // Inflate the layout for this fragment
        _binding = FragmentWindowBinding.inflate(inflater, container, false)
        return binding.root
    }

    // NOTE: fragments outlive their views!
    //       One must clean up any references to the binging class instance here
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textResult.setOnClickListener {
            (activity as MainActivity).openBottomSheet()
        }

        binding.filterImage.setOnClickListener {
            if (currentState == BottomSheetBehavior.STATE_EXPANDED) {
                (activity as MainActivity).closeBottomSheet()
            } else {
                (activity as MainActivity).openBottomSheet()
            }
        }
    }

    override fun onStateChanged(bottomSheet: View, newState: Int) {
        currentState = newState
        when (newState) {
            BottomSheetBehavior.STATE_EXPANDED -> {
                binding.textResult.text = getString(R.string.zero_results)
                binding.filterImage.setImageResource(R.drawable.ic_baseline_filter_list_24)
            }
            BottomSheetBehavior.STATE_COLLAPSED -> {
                binding.textResult.text = getString(R.string.see_the_results)
                binding.filterImage.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24)
            }
            // TODO: when the bottom sheet is moving update data
        }
    }
}
