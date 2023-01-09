package com.gooddayjobber.ui.MainScreen

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class LendersGridItemDecorator(
    private val spanCount: Int,
    private val spacing: Int,
    private val includeEdge: Boolean = false
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        val pos: Int = parent.getChildAdapterPosition(view)
        val column: Int = pos % spanCount

        if (includeEdge) {
            outRect.left = spacing - column * spacing / spanCount
            outRect.right = (column + 1)

            if (pos < spanCount) {
                outRect.top = spacing
            }
            outRect.bottom = spacing
        } else {
            outRect.left = column * spacing / spanCount
            outRect.right = spacing - (column + 1) * spacing / spanCount
            if (pos >= spanCount) {
                outRect.top = spacing
            }
        }
    }
}