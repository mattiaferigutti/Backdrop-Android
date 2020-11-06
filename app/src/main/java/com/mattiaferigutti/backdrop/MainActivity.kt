package com.mattiaferigutti.backdrop

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.button.MaterialButton
import com.google.android.material.slider.RangeSlider
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var listener: OnBottomSheetCallbacks? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //removing the shadow from the action bar
        supportActionBar?.elevation = 0f

        configureBackdrop()
        setToggleMenuButtons()
        setRangerSlider()
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
        materialButtonToggleGroupSort.addOnButtonCheckedListener { _, checkedId, _ ->
            toggleButton(findViewById(checkedId))
        }
        materialButtonToggleGroupDifficulty.addOnButtonCheckedListener { _, checkedId, _ ->
            toggleButton(findViewById(checkedId))
        }
    }

    private fun setRangerSlider() {
        lengthSlider.addOnChangeListener { rangeSlider, value, fromUser ->
            // Responds to when slider's value is changed
            lengthTextView.text = "${rangeSlider.values[0].toInt()} km - ${rangeSlider.values[1].toInt()} km"
            if (rangeSlider.values[1].toInt() == 150) {
                lengthTextView.text = lengthTextView.text.toString() + "+"
            }
        }

        lengthSlider.addOnSliderTouchListener(object : RangeSlider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: RangeSlider) {
                // Responds to when slider's touch event is being started
            }

            override fun onStopTrackingTouch(slider: RangeSlider) {
                // Responds to when slider's touch event is being stopped
            }
        })
    }

    private fun handleCheckedButtonsInSort() {
        when {
            materialButtonToggleGroupSort.checkedButtonIds.contains(R.id.mostPopularButton) -> {

            }
            materialButtonToggleGroupSort.checkedButtonIds.contains(R.id.closestButton) -> {

            }
        }
    }

    private fun handleCheckedButtonsInDifficulty() {
        when {
            materialButtonToggleGroupDifficulty.checkedButtonIds.contains(R.id.easyButton) -> {

            }
            materialButtonToggleGroupDifficulty.checkedButtonIds.contains(R.id.moderateButton) -> {

            }
            materialButtonToggleGroupDifficulty.checkedButtonIds.contains(R.id.hardButton) -> {

            }
        }
    }

    private fun toggleButton(button: MaterialButton) {
        if (button.textColors.defaultColor == ContextCompat.getColor(this, R.color.white)) {
            button.strokeColor = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.selected_item))
            button.setTextColor(ContextCompat.getColor(this, R.color.selected_item))
        } else {
            button.strokeColor = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))
            button.setTextColor(ContextCompat.getColor(this, R.color.white))
        }
    }

    private fun configureBackdrop() {
        val fragment = supportFragmentManager.findFragmentById(R.id.filter_fragment)

        fragment?.view?.let {
            BottomSheetBehavior.from(it).let { bs ->

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