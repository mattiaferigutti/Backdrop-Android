package com.mattiaferigutti.backdrop

import android.view.View

interface OnBottomSheetCallbacks {
    fun onStateChanged(bottomSheet: View, newState: Int)
}