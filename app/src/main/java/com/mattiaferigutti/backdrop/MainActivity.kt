package com.mattiaferigutti.backdrop

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior

class MainActivity : AppCompatActivity() {
    private var listener: OnBottomSheetCallbacks? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //removing the shadow from the action bar
        supportActionBar?.elevation = 0f

        configureBackdrop()
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

    private fun configureBackdrop() {
        val fragment = supportFragmentManager.findFragmentById(R.id.filter_fragment)

        fragment?.let {
            BottomSheetBehavior.from(it.view!!)?.let { bsb ->

                bsb.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                    override fun onSlide(bottomSheet: View, slideOffset: Float) {}

                    override fun onStateChanged(bottomSheet: View, newState: Int) {
                        listener?.onStateChanged(bottomSheet, newState)
                    }
                })

                bsb.state = BottomSheetBehavior.STATE_EXPANDED

                mBottomSheetBehavior = bsb
            }
        }
    }

    override fun onBackPressed() {
        mBottomSheetBehavior?.let {
            if (it.state == BottomSheetBehavior.STATE_EXPANDED) {
                it.state = BottomSheetBehavior.STATE_COLLAPSED
            } else {
                super.onBackPressed()
            }
        } ?: super.onBackPressed()
    }
}