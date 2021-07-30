package com.mattiaferigutti.backdrop

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.button.MaterialButton
import com.google.android.material.slider.RangeSlider
import com.mattiaferigutti.backdrop.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    // This property is only valid between `onCreateView` and `onDestroyView`
    private val binding get() = _binding!!

    private var listener: OnBottomSheetCallbacks? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //removing the shadow from the action bar
        supportActionBar?.elevation = 0f

        configureBackdrop()
        setToggleMenuButtons()
        setRangerSlider()
    }

    // NOTE: fragments outlive their views!
    //       One must clean up any references to the binging class instance here
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun setOnBottomSheetCallbacks(onBottomSheetCallbacks: OnBottomSheetCallbacks) {
        this.listener = onBottomSheetCallbacks
    }

    fun closeBottomSheet() {
        mBottomSheetBehavior?.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    fun openBottomSheet() {
        mBottomSheetBehavior?.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private var mBottomSheetBehavior: BottomSheetBehavior<View?>? = null

    private fun setToggleMenuButtons() {
        binding.materialButtonToggleGroupSort.addOnButtonCheckedListener { _, checkedId, _ ->
            toggleButton(findViewById(checkedId))
        }
        binding.materialButtonToggleGroupDifficulty.addOnButtonCheckedListener { _, checkedId, _ ->
            toggleButton(findViewById(checkedId))
        }
    }

    private fun setRangerSlider() {
        binding.lengthSlider.addOnChangeListener { rangeSlider, /*value*/ _, /*fromUser*/ _ ->
            // Responds to when slider's value is changed
            binding.lengthTextView.text = getString(R.string.length_range_current,
                rangeSlider.values[0].toInt(), rangeSlider.values[1].toInt())
            if (rangeSlider.values[1].toInt() == 150) {
                binding.lengthTextView.text = getString(
                    R.string.length_max,
                    binding.lengthTextView.text.toString()
                )
            }
        }

        binding.lengthSlider.addOnSliderTouchListener(object : RangeSlider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: RangeSlider) {
                // Responds to when slider's touch event is being started
            }

            override fun onStopTrackingTouch(slider: RangeSlider) {
                // Responds to when slider's touch event is being stopped
            }
        })
    }

    /*private fun handleCheckedButtonsInSort() {
        when {
            materialButtonToggleGroupSort.checkedButtonIds.contains(R.id.mostPopularButton) -> {

            }
            materialButtonToggleGroupSort.checkedButtonIds.contains(R.id.closestButton) -> {

            }
        }
    }*/

    /*private fun handleCheckedButtonsInDifficulty() {
        when {
            materialButtonToggleGroupDifficulty.checkedButtonIds.contains(R.id.easyButton) -> {

            }
            materialButtonToggleGroupDifficulty.checkedButtonIds.contains(R.id.moderateButton) -> {

            }
            materialButtonToggleGroupDifficulty.checkedButtonIds.contains(R.id.hardButton) -> {

            }
        }
    }*/

    private fun toggleButton(button: MaterialButton) {
        if (button.textColors.defaultColor == ContextCompat.getColor(this, R.color.white)) {
            button.strokeColor =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.selected_item))
            button.setTextColor(ContextCompat.getColor(this, R.color.selected_item))
        } else {
            button.strokeColor = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))
            button.setTextColor(ContextCompat.getColor(this, R.color.white))
        }
    }

    private fun configureBackdrop() {
        val fragment = supportFragmentManager.findFragmentById(R.id.filter_fragment)

        fragment?.view?.let { view ->
            BottomSheetBehavior.from(view).let { bs ->

                bs.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                    override fun onSlide(bottomSheet: View, slideOffset: Float) {}

                    override fun onStateChanged(bottomSheet: View, newState: Int) {
                        listener?.onStateChanged(bottomSheet, newState)
                    }
                })

                bs.state = BottomSheetBehavior.STATE_EXPANDED

                mBottomSheetBehavior = bs
            }
        }
    }
}
