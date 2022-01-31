package ru.skillbranch.gameofthrones.presentation.view.common

import android.content.Context
import android.util.AttributeSet
import android.widget.Button
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import ru.skillbranch.gameofthrones.R

/**
 * @author Valeriy Minnulin
 */
class ParentInfoField @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val parentType: TextView
    private val parentButton: Button

    init {
        inflate(context, R.layout.parent_info_field, this)
        parentType = findViewById(R.id.parent_type)
        parentButton = findViewById(R.id.go_to_parent_button)
    }

    fun setButtonClickListener(listener: OnClickListener) {
        parentButton.setOnClickListener(listener)
    }

    fun setParentButtonText(text: String) {
        parentButton.text = text
    }

    fun setParentTypeText(text: String) {
        parentType.text = text
    }

    fun setParentButtonColor(@ColorRes colorRes: Int) {
        ViewCompat.setBackgroundTintList(parentButton, ContextCompat.getColorStateList(this.context, colorRes))
        parentButton.setTextColor(ContextCompat.getColor(this.context, android.R.color.white))
    }
}