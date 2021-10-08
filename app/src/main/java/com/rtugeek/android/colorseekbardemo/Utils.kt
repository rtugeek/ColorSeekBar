package com.rtugeek.android.colorseekbardemo

import android.content.Context

object Utils {

    fun dp2px(context: Context, dpValue: Float): Int {
        val scale: Float = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }
}