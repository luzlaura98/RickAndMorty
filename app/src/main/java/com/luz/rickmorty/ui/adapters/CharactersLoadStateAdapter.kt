package com.luz.rickmorty.ui.adapters

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

/**
 * Created by Luz on 7/8/2022.
 */
class CharactersLoadStateAdapter(
    private val adapter: CharactersAdapter
) : LoadStateAdapter<NetworkStateItemViewHolder>(){

    override fun onBindViewHolder(holder: NetworkStateItemViewHolder, loadState: LoadState) {
        holder.bindTo(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): NetworkStateItemViewHolder {
        return NetworkStateItemViewHolder(parent){
            adapter.retry()
        }
    }

}