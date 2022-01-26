package ru.skillbranch.gameofthrones.presentation.view.characters

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Point
import android.util.AttributeSet
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.core.animation.doOnEnd

/**
 * Класс для смены цвета фона с анимацией кругом
 *
 * @author Valeriy Minnulin
 */
class TabLayoutAnimationView: FrameLayout {

    private var animationData: AnimationData? = null
    private var currentAnimationValue: Float = 0f

    private val paint = Paint()

    private var displayWidth: Float = 1000f

    private var currentAnimator: ValueAnimator? = null

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    )

    init {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        val point = Point()
        display.getSize(point)
        displayWidth = point.x.toFloat()
    }

    /**
     * Запустить анимацию смены цвета с кругом, передав информацию о цвете и координате круга в [animationData]
     */
    fun animateColoredCircle(animationData: AnimationData) {
        this.animationData = animationData
        paint.color = animationData.animatedColor

        currentAnimator?.cancel()
        currentAnimator = ValueAnimator.ofFloat(0f, displayWidth).apply {
            duration = 300L
            addUpdateListener {
                val value = it.animatedValue as Float
                currentAnimationValue = value
                invalidate()
            }
            doOnEnd {
                currentAnimationValue = 0f
                this@TabLayoutAnimationView.animationData = null
                setBackgroundColor(animationData.animatedColor)
            }
            start()
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val localAnimation = animationData ?: return
        canvas?.drawCircle(localAnimation.circleX, localAnimation.circleY, currentAnimationValue, paint)
    }

    data class AnimationData(
        val animatedColor: Int,
        val circleX: Float,
        val circleY: Float
    )
}