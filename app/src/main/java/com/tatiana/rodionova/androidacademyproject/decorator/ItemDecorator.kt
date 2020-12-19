package com.tatiana.rodionova.androidacademyproject.decorator

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ItemDecorator(private val resource: Int, private val spanCount: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val margin = parent.context.resources.getDimension(resource).toInt()
        val position = parent.getChildAdapterPosition(view)
        val column = position % spanCount
        val isFirstRow = position < spanCount

        outRect.left = column * margin / spanCount
        outRect.right = margin - (column + 1) * margin / spanCount

        if (!isFirstRow) {
            outRect.top = margin
        }
    }
}
