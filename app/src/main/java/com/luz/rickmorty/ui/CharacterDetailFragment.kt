package com.luz.rickmorty.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.luz.rickmorty.R
import com.luz.rickmorty.data.model.Character
import com.luz.rickmorty.databinding.FragmentCharacterDetailBinding
import com.luz.rickmorty.ui.utils.AttributeCharacterView
import com.luz.rickmorty.ui.utils.StatusConverter

/**
 * Created by Luz on 9/8/2022.
 */
class CharacterDetailFragment : Fragment() {

    companion object {
        const val EXTRA_CHARACTER = "extra_character"
        @JvmStatic
        fun newInstance(character: Character): CharacterDetailFragment {
            val fragment = CharacterDetailFragment()
            fragment.arguments = bundleOf(EXTRA_CHARACTER to character)
            return fragment
        }
    }

    private var _binding: FragmentCharacterDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharacterDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getParcelable<Character>(EXTRA_CHARACTER)?.let { show(it) }
    }

    fun show(character: Character){
        context?:return; _binding?:return
        with(binding){
            Glide.with(requireContext()).load(character.image).centerCrop().into(ivCharacter)
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