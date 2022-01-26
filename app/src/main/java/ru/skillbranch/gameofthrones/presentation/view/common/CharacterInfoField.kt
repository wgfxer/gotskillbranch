package ru.skillbranch.gameofthrones.presentation.view.common

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import ru.skillbranch.gameofthrones.R

/**
 * @author Valeriy Minnulin
 */
class CharacterInfoField @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var iconImage: ImageView
    private val fieldName: TextView
    private val fieldContent: TextView

    init {
        inflate(context, R.layout.character_info_field, this)
        iconImage = findViewById(R.id.icon)
        fieldName = findViewById(R.id.field_name)
        fieldContent = findViewById(R.id.field_content)

        context.obtainStyledAttributes(attrs, R.styleable.CharacterInfoField).use {
            setFieldNameText(it.getString(R.styleable.CharacterInfoField_fieldName))
        }
    }

    fun setIconColor(@ColorRes colorRes: Int) {
        iconImage.setColorFilter(ContextCompat.getColor(context, colorRes))
    }

    fun setFieldNameText(text: String?) {
        fieldName.text = text
    }

    fun setFieldNameText(@StringRes textResId: Int) {
        fieldName.setText(textResId)
    }

    fun setFieldContentText(text: String) {
        fieldContent.text = text
    }

    fun setFieldContentText(@StringRes textResId: Int) {
        fieldContent.setText(textResId)
    }
}