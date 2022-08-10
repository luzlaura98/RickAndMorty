package com.luz.rickmorty.ui

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.luz.rickmorty.R
import com.luz.rickmorty.data.model.Character
import com.luz.rickmorty.databinding.FragmentCharacterPreviewBinding
import com.luz.rickmorty.ui.utils.AttributeCharacterView
import com.luz.rickmorty.ui.utils.StatusConverter
import com.luz.rickmorty.ui.utils.isDarkTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Luz on 9/8/2022.
 */
@AndroidEntryPoint
class CharacterPreviewFragment : Fragment() {
    companion object {
        const val EXTRA_CHARACTER = "EXTRA_CHARACTER"
        @JvmStatic
        fun newInstance(character: Character): CharacterPreviewFragment {
            val fragment = CharacterPreviewFragment()
            fragment.arguments = bundleOf(EXTRA_CHARACTER to character)
            return fragment
        }
    }

    private var _binding: FragmentCharacterPreviewBinding? = null
    private val binding get() = _binding!!

    var drawablePlaceholder : ColorDrawable?  = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharacterPreviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.root.isVisible = false
        arguments?.getParcelable<Character>(EXTRA_CHARACTER)?.let { show(it) }
    }

    fun show(character: Character){
        context?:return

        // If view was not created yet, pass args
        if (_binding == null){
            arguments = bundleOf(EXTRA_CHARACTER to character)
            return
        }

        // If character is the previous one, no need to update
        if (character.id == binding.root.tag)
            return

        // Showing first character
        if (!binding.root.isVisible){
            bindCharacter(character)
            binding.root.isVisible = true
            binding.root.alpha = 0f
            binding.root.animateFadeIn()
            return
        }

        // Hide previous character and show the new one
        binding.root.animateFadeOut {
            bindCharacter(character)
            binding.root.animateFadeIn()
        }
    }

    private fun bindCharacter(character: Character){
        with(binding){
            if (drawablePlaceholder == null){
                drawablePlaceholder = ColorDrawable(
                    ContextCompat.getColor(binding.root.context,
                    if (isDarkTheme(resources))
                        R.color.onBackgroundDarkColor
                    else R.color.onBackgroundLightColor
                    )
                )
            }
            binding.root.tag = character.id
            tvCharacterName.text = character.name
            Glide.with(requireContext())
                .load(character.image)
                .placeholder(drawablePlaceholder)
                .centerCrop()
                .into(ivCharacter)

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

    private fun View.animateFadeOut(endFun : (() -> Unit)? = null){
        animate().apply {
            alpha(0f)
            duration = 100
            interpolator = AccelerateDecelerateInterpolator()
            if (endFun != null){
                withEndAction {
                    endFun.invoke()
                }
            }
        }.start()
    }

    private fun View.animateFadeIn(endFun : (() -> Unit)? = null){
        animate().apply {
            alpha(1f)
            duration = 400
            interpolator = AccelerateDecelerateInterpolator()
            if (endFun != null){
                withEndAction {
                    endFun.invoke()
                }
            }
        }.start()
    }

    private fun AttributeCharacterView.show(text: String?) {
        body = if (text.isNullOrBlank())
            getString(R.string.unknown)
        else
            text.replaceFirstChar(Char::titlecase)
    }
}