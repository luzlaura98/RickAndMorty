package com.luz.rickmorty.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.navigation.fragment.NavHostFragment
import com.luz.rickmorty.data.model.Character
import com.luz.rickmorty.databinding.ActivityCharacterDetailBinding
import com.luz.rickmorty.ui.base.BaseActivity
import com.luz.rickmorty.ui.utils.isLandscape
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Luz on 8/8/2022.
 */
@AndroidEntryPoint
class CharacterDetailActivity : BaseActivity() {

    private lateinit var binding : ActivityCharacterDetailBinding

    companion object {
        private const val EXTRA_CHARACTER = "extra_character"
        fun buildIntent(context: Context, character : Character) : Intent{
            return Intent(context, CharacterDetailActivity::class.java)
                .putExtra(EXTRA_CHARACTER, character)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (isLandscape(resources)){
            finish()
            return
        }

        binding = ActivityCharacterDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        intent.getParcelableExtra<Character>(EXTRA_CHARACTER)?.let { character ->
            supportActionBar?.title = character.name
            //val navHostController = NavHostFragment.findNavController(binding.fragmentContainerView.getFragment()) //as NavHostController
            val f = binding.fragmentContainerView.getFragment<NavHostFragment>().childFragmentManager.fragments.firstOrNull()
            (f as? CharacterDetailFragment)?.arguments = bundleOf(CharacterDetailFragment.EXTRA_CHARACTER to character)
        }
    }

}