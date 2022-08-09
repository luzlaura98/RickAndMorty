package com.luz.rickmorty.ui.utils

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.luz.rickmorty.R

/**
 * Created by Luz on 5/8/2022.
 */
class StatusView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    companion object {
        private val DEFAULT_STATUS = Status.Unknown
    }

    enum class Status {
        Unknown, Alive, Dead
    }

    var currentStatus: Status = DEFAULT_STATUS
        set(value) {
            context ?: return
            text = context.getString(
                when (value) {
                    Status.Unknown -> R.string.status_unknown
                    Status.Alive -> R.string.status_alive
                    Status.Dead -> R.string.status_dead
                }
            )
            setCompoundDrawablesWithIntrinsicBounds(
                when(value){
                    Status.Unknown -> R.drawable.ic_status_unknown
                    Status.Alive -> R.drawable.ic_status_alive
                    Status.Dead -> R.drawable.ic_status_dead
                           }
                ,0,0,0)
            field = value
            invalidate()
            requestLayout()
        }

    init {
        if (attrs != null) {
            val values = context.theme.obtainStyledAttributes(attrs, R.styleable.StatusView, 0, 0)
            try {
                val valueStatus =
                    values.getInteger(R.styleable.StatusView_status, DEFAULT_STATUS.ordinal)
                currentStatus = Status.values()[valueStatus]
            } finally {
                values.recycle()
            }
        }
    }
}