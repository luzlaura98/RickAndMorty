package com.luz.rickmorty.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.luz.rickmorty.R
import com.luz.rickmorty.databinding.NetworkStateItemBinding

/**
 * Created by Luz on 7/8/2022.
 */
class NetworkStateItemViewHolder(
    parent: ViewGroup,
    private val retryCallback: () -> Unit
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.network_state_item, parent, false)
) {
    private val binding = NetworkStateItemBinding.bind(itemView)

    init {
        binding.btnRetry.setOnClickListener { retryCallback() }
    }

    fun bindTo(loadState: LoadState) {
        with(binding){
            pbLoading.isVisible = loadState is LoadState.Loading
            btnRetry.isVisible = loadState is LoadState.Error
            tvErrorMessage.text = (loadState as? LoadState.Error)?.error?.message
            tvErrorMessage.isVisible = !(loadState as? LoadState.Error)?.error?.message.isNullOrBlank()
        }
    }
}