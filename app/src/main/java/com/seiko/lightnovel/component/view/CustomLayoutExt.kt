package com.seiko.lightnovel.component.view

import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop

context(View)
inline val Int.dp: Int
    get() = toFloat().dp

context(View)
val Float.dp: Int
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        toFloat(),
        resources.displayMetrics
    ).toInt()

context(View)
inline val Int.sp: Float
    get() = toFloat().sp

context(View)
val Float.sp: Float
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        toFloat(),
        resources.displayMetrics
    )

context(CustomLayout)
fun View.autoMeasure(
    widthMeasureSpec: Int = defaultWidthMeasureSpec(parentView = this@CustomLayout),
    heightMeasureSpec: Int = defaultHeightMeasureSpec(parentView = this@CustomLayout)
) {
    measure(widthMeasureSpec, heightMeasureSpec)
}

context(CustomLayout)
fun View.defaultWidthMeasureSpec(parentView: ViewGroup): Int = when (layoutParams.width) {
    CustomLayout.MATCH_PARENT -> (parentView.measuredWidth - parentView.paddingStart - parentView.paddingEnd).toExactlyMeasureSpec()
    CustomLayout.WRAP_CONTENT -> (parentView.measuredWidth - parentView.paddingStart - parentView.paddingEnd).toAtMostMeasureSpec()
    0 -> throw IllegalAccessException("Need special treatment for $this")
    else -> layoutParams.width.toExactlyMeasureSpec()
}

context(CustomLayout)
fun View.defaultHeightMeasureSpec(parentView: ViewGroup): Int = when (layoutParams.height) {
    CustomLayout.MATCH_PARENT -> (parentView.measuredHeight - parentView.paddingTop - parentView.paddingBottom).toExactlyMeasureSpec()
    CustomLayout.WRAP_CONTENT -> (parentView.measuredHeight - parentView.paddingTop - parentView.paddingBottom).toAtMostMeasureSpec()
    0 -> throw IllegalAccessException("Need special treatment for $this")
    else -> layoutParams.height.toExactlyMeasureSpec()
}

context(CustomLayout)
fun Int.toExactlyMeasureSpec(): Int {
    return View.MeasureSpec.makeMeasureSpec(this, View.MeasureSpec.EXACTLY)
}

context(CustomLayout)
fun Int.toAtMostMeasureSpec(): Int {
    return View.MeasureSpec.makeMeasureSpec(this, View.MeasureSpec.AT_MOST)
}

context(CustomLayout)
inline val View.measureWidthWithMargins
    get() = measuredWidth + leftMargin + rightMargin

context(CustomLayout)
inline val View.measureHeightWithMargins
    get() = measuredHeight + topMargin + bottomMargin

context(CustomLayout)
inline val View.leftMargin: Int
    get() = marginLeft

context(CustomLayout)
inline val View.topMargin: Int
    get() = marginTop

context(CustomLayout)
inline val View.rightMargin: Int
    get() = marginRight

context(CustomLayout)
inline val View.bottomMargin: Int
    get() = marginBottom
