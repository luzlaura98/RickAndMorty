package com.luz.rickmorty.ui.base

import android.content.Context
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar
import com.luz.rickmorty.R
import com.luz.rickmorty.databinding.PartialPlaceholderBinding

/**
 * Created by Luz on 3/8/2022.
 */
abstract class BaseActivity : AppCompatActivity() {

    val context: Context get() = this

    open val partialPlaceholderBinding: PartialPlaceholderBinding? = null
    open val onRetry: (() -> Unit)? = null

    private var snackErrorMessage: Snackbar? = null

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    protected open fun showLoading(isLoading: Boolean) {
        partialPlaceholderBinding?.apply {
            if (isLoading) {
                root.isVisible = true
                pbLoading.isVisible = true
                ivPlaceholder.isVisible = false
                tvPlaceholderTitle.isVisible = false
                tvPlaceholderMessage.isVisible = false
                btnRetry.isVisible = false
            } else
                root.isVisible = false
        }
    }

    protected open fun showPlaceholderMessage(message: String?, onRetry: (() -> Unit)?) {
        partialPlaceholderBinding?.apply {
            root.isVisible = true
            pbLoading.isVisible = false
            ivPlaceholder.isVisible = true
            tvPlaceholderTitle.isVisible = true
            tvPlaceholderMessage.text =
                message ?: getString(R.string.default_message_error)
            tvPlaceholderMessage.isVisible = true
            btnRetry.isVisible =
                if (onRetry != null) {
                    btnRetry.setOnClickListener { onRetry.invoke() }
                    true
                } else
                    false
        }
    }

    fun showSnackErrorMessage(message: String?) {
        if (snackErrorMessage == null) {
            snackErrorMessage = Snackbar.make(
                findViewById(android.R.id.content),
                message ?: getString(R.string.default_message_error),
                Snackbar.LENGTH_INDEFINITE
            )
            snackErrorMessage?.apply {
                setAction(getString(R.string.ok_)) { snackErrorMessage?.dismiss() }
                val textView =
                    view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
                textView.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_error_outline,
                    0,
                    0,
                    0
                )
                textView.compoundDrawablePadding =
                    resources.getDimensionPixelOffset(R.dimen.snackbar_icon_padding)
                show()
            }
        }
        snackErrorMessage?.apply {
            setText(message ?: getString(R.string.default_message_error))
            show()
        }
    }

}