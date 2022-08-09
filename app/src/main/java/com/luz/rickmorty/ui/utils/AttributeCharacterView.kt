package com.luz.rickmorty.ui.utils

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.luz.rickmorty.R
import com.luz.rickmorty.databinding.ViewAttributeCharacterBinding

/**
 * Created by Luz on 8/8/2022.
 */
class AttributeCharacterView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var binding: ViewAttributeCharacterBinding =
        ViewAttributeCharacterBinding.inflate(LayoutInflater.from(context))

    var title: String? = null
        set(value) {
            binding.tvTitle.text = value
            field = value
            invalidate()
            requestLayout()
        }

    var body: String? = null
        set(value) {
            binding.tvBody.text = value
            field = value
            invalidate()
            requestLayout()
        }

    init {
        addView(binding.root)
        if (attrs != null) {
            val values = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.AttributeCharacterView,
                0,
                0
            )
            try {
                val valueTitle = values.getString(R.styleable.AttributeCharacterView_title)
                title = valueTitle
                val valueBody = values.getString(R.styleable.AttributeCharacterView_body)
                body  = valueBody
            } finally {
                values.recycle()
            }
        }
    }

}