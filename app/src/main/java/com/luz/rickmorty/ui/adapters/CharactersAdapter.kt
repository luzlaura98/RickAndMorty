package com.luz.rickmorty.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.luz.rickmorty.data.model.Character
import com.luz.rickmorty.databinding.ItemCharacterBinding
import com.luz.rickmorty.ui.utils.StatusConverter

/**
 * Created by Luz on 3/8/2022.
 */
class CharactersAdapter(
    private val onClick: (character: Character) -> Unit
) : PagingDataAdapter<Character, CharacterViewHolder>(POST_COMPARATOR) {

    companion object {
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<Character>() {
            override fun areItemsTheSame(oldItem: Character, newItem: Character) =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: Character, newItem: Character) =
                oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder.create(parent, onClick)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class CharacterViewHolder(
    private val binding: ItemCharacterBinding,
    private val onClick: (character: Character) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun create(
            parent: ViewGroup,
            onClick: (character: Character) -> Unit
        ): CharacterViewHolder {
            val binding = ItemCharacterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return CharacterViewHolder(binding, onClick)
        }
    }

    fun bind(item: Character?) {
        with(binding) {
            tvCharacterName.text = item?.name
            Glide.with(binding.root) //add thumbnail, add placeholder
                .load(item?.image)
                .into(ivCharacter)
            root.setOnClickListener {
                if (item != null) onClick(item)
            }
            statusView?.apply {
                currentStatus = StatusConverter.from(item?.status)
            }
        }
    }
}