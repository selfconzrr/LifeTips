package com.example.skeleton

import android.support.annotation.ColorRes
import android.support.annotation.IntRange
import android.support.annotation.LayoutRes
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.supercharge.shimmerlayout.ShimmerLayout

class ViewSkeletonScreen(builder: Builder) : SkeletonScreen {

    companion object {
        var TAG: String = ViewSkeletonScreen::class.java.name
    }

    var mActualView: View = builder.mView
    var mSkeletonResID: Int = builder.mSkeletonLayoutResID
    var mShimmerColor: Int = builder.mShimmerColor
    var mShimmerAngle: Int = builder.mShimmerAngle
    var mShimmerDuration: Int = builder.mShimmerDuration
    var mShimmer: Boolean = builder.mShimmer
    private var mViewReplacer = ViewReplacer(mActualView)

    private fun generateShimmerContainerLayout(parentView: ViewGroup): ShimmerLayout? {
        val shimmerLayout = LayoutInflater.from(mActualView.context).inflate(R.layout.layout_shimmer, parentView, false) as ShimmerLayout
        shimmerLayout.setShimmerColor(mShimmerColor)
        shimmerLayout.setShimmerAngle(mShimmerAngle)
        shimmerLayout.setShimmerAnimationDuration(mShimmerDuration)
        val innerView = LayoutInflater.from(mActualView.context).inflate(mSkeletonResID, shimmerLayout, false)
        val lp = innerView.layoutParams
        if (lp != null) {
            shimmerLayout.layoutParams = lp
        }
        shimmerLayout.addView(innerView)
        shimmerLayout.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(v: View) {
                shimmerLayout.startShimmerAnimation()
            }

            override fun onViewDetachedFromWindow(v: View) {
                shimmerLayout.stopShimmerAnimation()
            }
        })
        shimmerLayout.startShimmerAnimation()
        return shimmerLayout
    }

    private fun generateSkeletonLoadingView(): View? {
        val viewParent = mActualView.parent
        if (viewParent == null) {
            Log.e(TAG, "the source view have not attach to any view")
            return null
        }
        val parentView = viewParent as ViewGroup
        return if (mShimmer) {
            generateShimmerContainerLayout(parentView)
        } else LayoutInflater.from(mActualView.context).inflate(mSkeletonResID, parentView, false)
    }

    override fun show() {
        var skeletonLoadingView = generateSkeletonLoadingView()
        if (skeletonLoadingView != null) {
            mViewReplacer.replace(skeletonLoadingView)
        }
    }

    override fun hide() {
        if (mViewReplacer.getTargetView() is ShimmerLayout) {
            (mViewReplacer.getTargetView() as ShimmerLayout).stopShimmerAnimation()
        }
    }

    class Builder(val mView: View) {
        var mSkeletonLayoutResID = 0
        var mShimmer = true
        var mShimmerColor: Int
        var mShimmerDuration = 1000
        var mShimmerAngle = 20

        /**
         * @param skeletonLayoutResID the loading skeleton layoutResID
         */
        fun load(@LayoutRes skeletonLayoutResID: Int): Builder {
            mSkeletonLayoutResID = skeletonLayoutResID
            return this
        }

        /**
         * @param shimmerColor the shimmer color
         */
        fun color(@ColorRes shimmerColor: Int): Builder {
            mShimmerColor = ContextCompat.getColor(mView.context, shimmerColor)
            return this
        }

        /**
         * @param shimmer whether show shimmer animation
         */
        fun shimmer(shimmer: Boolean): Builder {
            mShimmer = shimmer
            return this
        }

        /**
         * the duration of the animation , the time it will take for the highlight to move from one end of the layout
         * to the other.
         *
         * @param shimmerDuration Duration of the shimmer animation, in milliseconds
         */
        fun duration(shimmerDuration: Int): Builder {
            mShimmerDuration = shimmerDuration
            return this
        }

        /**
         * @param shimmerAngle the angle of the shimmer effect in clockwise direction in degrees.
         */
        fun angle(@IntRange(from = 0, to = 30) shimmerAngle: Int): Builder {
            mShimmerAngle = shimmerAngle
            return this
        }

        fun show(): ViewSkeletonScreen {
            val skeletonScreen = ViewSkeletonScreen(this)
            skeletonScreen.show()
            return skeletonScreen
        }

        init {
            mShimmerColor = ContextCompat.getColor(mView.context, R.color.shimmer_color)
        }
    }
}