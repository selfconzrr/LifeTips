package com.example.skeleton

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class ViewReplacer(sourceView: View) {

    private val TAG = ViewReplacer::class.java.name

    private val mSourceView: View = sourceView
    private var mTargetView: View? = null
    private var mTargetViewResID = -1
    private var mCurrentView: View = mSourceView
    private var mSourceParentView: ViewGroup? = null
    private var mSourceViewLayoutParams: ViewGroup.LayoutParams = mSourceView.layoutParams
    private var mSourceViewIndexInParent = 0
    private var mSourceViewId = mSourceView.id

    fun replace(targetViewResID: Int) {
        if (mTargetViewResID == targetViewResID) {
            return
        }
        if (init()) {
            mTargetViewResID = targetViewResID
            replace(LayoutInflater.from(mSourceView.context).inflate(mTargetViewResID, mSourceParentView, false))
        }
    }

    fun replace(targetView: View) {
        if (mCurrentView == targetView) {
            return
        }

        if (targetView.parent != null) {
            (targetView.parent as ViewGroup).removeView(targetView)
        }

        if (init()) {
            mTargetView = targetView
            mSourceParentView?.removeView(mSourceView)
            mTargetView?.id = mSourceViewId
            mSourceParentView?.addView(mTargetView, mSourceViewIndexInParent, mSourceViewLayoutParams)
            mCurrentView = targetView // mTargetView
        }
    }

    fun restore() {
        mSourceParentView?.let {
            it.removeView(mCurrentView)
            it.addView(mSourceView, mSourceViewIndexInParent)
            mCurrentView = mSourceView
            mTargetView = null
            mTargetViewResID = -1
        }
    }

    fun init(): Boolean {
        if (mSourceParentView == null) {
            mSourceParentView = mSourceView.parent as ViewGroup

            if (mSourceParentView == null) {
                Log.e(TAG, "the source view have not attach to any view")
                return false
            }

            var count = mSourceParentView?.childCount ?: 0
            for (index in 0 until count) {
                if (mSourceView == mSourceParentView?.getChildAt(index)) {
                    mSourceViewIndexInParent = index
                    break
                }
            }
        }
        return true
    }

    fun getTargetView(): View? {
        return mTargetView
    }

    fun getSourceView(): View? {
        return mSourceView
    }

    fun getCurrentView(): View {
        return mCurrentView
    }
}