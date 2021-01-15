package com.example.skeleton

import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * 骨架屏
 * 1、普通 view
 * skeletonScreen = Skeleton.bind(rootView)
 * .load(R.layout.activity_view_skeleton)
 * .duration(1000)
 * .color(R.color.shimmer_color)
 * .angle(0)
 * .show();
 *
 *
 * 2、recyclerView
 * final SkeletonScreen skeletonScreen = Skeleton.bind(recyclerView)
    .adapter(adapter)
    .shimmer(true)
    .angle(20)
    .frozen(false)
    .duration(1200)
    .count(10)
    .load(R.layout.item_skeleton_news)
    .show(); //default count is 10
 */
class Skeleton {

    fun bind(view: View): ViewSkeletonScreen.Builder {
        return ViewSkeletonScreen.Builder(view)
    }

    fun bind(recyclerView: RecyclerView): RecyclerViewSkeletonScreen.Builder {
        return RecyclerViewSkeletonScreen.Builder(recyclerView)
    }

}