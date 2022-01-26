package ru.skillbranch.gameofthrones.presentation.view.characters

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ru.skillbranch.gameofthrones.R

/**
 * @author Valeriy Minnulin
 */
class DividerItemDecoration: RecyclerView.ItemDecoration() {

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        val drawable: Drawable = ContextCompat.getDrawable(parent.context, R.drawable.common_divider) ?: return
        val dividerRect = Rect()
        drawable.getPadding(dividerRect)
        val left = parent.paddingLeft + dividerRect.left
        val right = parent.width - parent.paddingRight - dividerRect.right
        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)
            if (child != null) {
                val layoutParams = child.layoutParams as RecyclerView.LayoutParams
                val top = child.bottom + layoutParams.bottomMargin
                val bottom = top + drawable.intrinsicHeight
                drawable.bounds = Rect(left, top, right, bottom)
                drawable.draw(c)
            }
        }
    }
}