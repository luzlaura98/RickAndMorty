package com.luz.rickmorty.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.luz.rickmorty.R
import com.luz.rickmorty.data.model.Character
import com.luz.rickmorty.databinding.ActivityCharacterDetailBinding
import com.luz.rickmorty.ui.base.BaseActivity
import com.luz.rickmorty.ui.utils.AttributeCharacterView
import com.luz.rickmorty.ui.utils.StatusConverter

/**
 * Created by Luz on 8/8/2022.
 */
class CharacterDetailActivity : BaseActivity() {

    private lateinit var binding : ActivityCharacterDetailBinding

    companion object {
        private const val EXTRA_CHARACTER = "EXTRA_CHARACTER"
        fun buildIntent(context: Context, character : Character) : Intent{
            return Intent(context, CharacterDetailActivity::class.java)
                .putExtra(EXTRA_CHARACTER, character)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        intent.getParcelableExtra<Character>(EXTRA_CHARACTER)?.let { character ->
            show(character)
        }
    }

    private fun show(character: Character){
        supportActionBar?.title = character.name
        with(binding){
            //binding.tvCharacterName.text = character.name
            Glide.with(context).load(character.image).into(ivCharacter)

            val htmlEpisodesText = resources.getQuantityString(
                R.plurals.episodesCount, character.episodes.size,
                "<b>${character.name?:getString(R.string.unknown)}</b>",
                "<b>${character.episodes.size}</b>")

            tvQuantityEpisodes.text = HtmlCompat.fromHtml(htmlEpisodesText, HtmlCompat.FROM_HTML_MODE_COMPACT)
            statusView.currentStatus = StatusConverter.from(character.status)
            mSpecies.show(character.species)
            mType.show(character.type)
            mGender.show(character.gender)
            mOrigin.show(character.origin?.name)
            mLocation.show(character.location?.name)
        }
    }

    private fun AttributeCharacterView.show(text: String?) {
        body = if (text.isNullOrBlank())
            getString(R.string.unknown)
        else
            text.replaceFirstChar(Char::titlecase)
    }
}