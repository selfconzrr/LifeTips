package com.example.skeleton

import android.support.annotation.IntRange
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import io.supercharge.shimmerlayout.ShimmerLayout

class SkeletonAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var mItemCount = 0
    private var mLayoutReference = 0
    private var mLayoutArrayReferences: IntArray = intArrayOf()
    private var mColor = 0
    private var mShimmer = false
    private var mShimmerDuration = 0
    private var mShimmerAngle = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.getContext())
        if (doesArrayOfLayoutsExist()) {
            mLayoutReference = viewType
        }
        return if (mShimmer) {
            ShimmerViewHolder(inflater, parent, mLayoutReference)
        } else object : RecyclerView.ViewHolder(inflater.inflate(mLayoutReference, parent, false)) {}

    }

    override fun getItemViewType(position: Int): Int {
        return if (doesArrayOfLayoutsExist()) {
            getCorrectLayoutItem(position)
        } else super.getItemViewType(position)
    }

    override fun getItemCount(): Int {
        return mItemCount
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (mShimmer) {
            val layout = holder.itemView as ShimmerLayout
            layout.setShimmerAnimationDuration(mShimmerDuration)
            layout.setShimmerAngle(mShimmerAngle)
            layout.setShimmerColor(mColor)
            layout.startShimmerAnimation()
        }
    }

    fun setLayoutReference(layoutReference: Int) {
        mLayoutReference = layoutReference
    }

    fun setArrayOfLayoutReferences(layoutReferences: IntArray) {
        mLayoutArrayReferences = layoutReferences
    }

    fun setItemCount(itemCount: Int) {
        mItemCount = itemCount
    }

    fun setShimmerColor(color: Int) {
        mColor = color
    }

    fun shimmer(shimmer: Boolean) {
        mShimmer = shimmer
    }

    fun setShimmerDuration(shimmerDuration: Int) {
        mShimmerDuration = shimmerDuration
    }

    fun setShimmerAngle(@IntRange(from = 0, to = 30) shimmerAngle: Int) {
        mShimmerAngle = shimmerAngle
    }

    fun getCorrectLayoutItem(position: Int): Int {
        return if (doesArrayOfLayoutsExist()) {
            mLayoutArrayReferences[position % mLayoutArrayReferences.size]
        } else mLayoutReference
    }

    private fun doesArrayOfLayoutsExist(): Boolean {
        return mLayoutArrayReferences != null && mLayoutArrayReferences.size != 0
    }

}
