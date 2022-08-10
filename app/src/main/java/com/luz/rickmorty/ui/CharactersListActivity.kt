package com.luz.rickmorty.ui

import android.os.Bundle
import androidx.lifecycle.*
import kotlinx.coroutines.flow.collectLatest
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.luz.rickmorty.data.model.Character
import com.luz.rickmorty.data.network.parseError
import com.luz.rickmorty.data.repository.CharacterRepository
import com.luz.rickmorty.databinding.ActivityCharactersListBinding
import com.luz.rickmorty.databinding.PartialPlaceholderBinding
import com.luz.rickmorty.ui.adapters.CharactersAdapter
import com.luz.rickmorty.ui.adapters.CharactersLoadStateAdapter
import com.luz.rickmorty.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Luz on 3/8/2022.
 */
@AndroidEntryPoint
class CharactersListActivity : BaseActivity() {

    private lateinit var binding: ActivityCharactersListBinding

    private val previewFragment: CharacterPreviewFragment? by lazy {
        val fragmentManager = binding.fragmentContainerView?.getFragment<NavHostFragment>()?.childFragmentManager
        (fragmentManager?.fragments?.firstOrNull()) as? CharacterPreviewFragment
    }

    override val partialPlaceholderBinding: PartialPlaceholderBinding
        get() = PartialPlaceholderBinding.bind(binding.characterListContainer.placeholderContainer.root)

    override val onRetry: (() -> Unit)
        get() = { adapter.refresh() }

    private val adapter: CharactersAdapter by lazy { CharactersAdapter(this::onClickCharacter) }

    @Inject
    lateinit var characterRepository: CharacterRepository

    private val viewModel: CharacterListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharactersListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showLoading(false)
        initAdapter()
        initSwipeToRefresh()
        initFloatingActionButton()

        viewModel.lastCharacterSelected?.let { character ->
            if (binding.fragmentContainerView != null) {
                previewFragment?.show(character)
            }
        }
    }

    private fun initSwipeToRefresh() {
        binding.characterListContainer.swipeRefresh.apply {
            setOnRefreshListener {
                viewModel.lastCharacterSelected = null
                isRefreshing = false
                adapter.refresh()
            }
        }
    }

    private fun initFloatingActionButton() {
        binding.characterListContainer.fbScrollToTop.setOnClickListener {
            binding.characterListContainer.rvCharacters.smoothScrollToPosition(0)
        }
    }

    private fun initAdapter() {
        with(binding.characterListContainer) {
            rvCharacters.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            rvCharacters.addItemDecoration(
                DividerItemDecoration(
                    context,
                    LinearLayoutManager.VERTICAL
                )
            )
            rvCharacters.adapter = adapter.withLoadStateFooter(CharactersLoadStateAdapter(adapter))

            lifecycleScope.launch {
                viewModel.myFlow.collectLatest { pagingData ->
                    adapter.submitData(pagingData)
                }
            }

            lifecycleScope.launchWhenCreated {
                adapter.loadStateFlow
                    // Only emit when REFRESH LoadState for RemoteMediator changes.
                    .distinctUntilChangedBy { it.refresh }
                    // Scroll to top is synchronous with UI updates, even if remote load was
                    // triggered.
                    .collect { loadStates ->
                        when (loadStates.refresh) {
                            is LoadState.Loading -> {
                                showLoading(true)
                            }
                            is LoadState.Error -> {
                                // When LoadState is Error, it will not pass for NotLoading
                                val error = (loadStates.refresh as LoadState.Error).error
                                showPlaceholderMessage(error.parseError(context)) { adapter.refresh() }
                            }
                            is LoadState.NotLoading -> {
                                showLoading(false)
                                binding.fragmentContainerView?.let {
                                    if (viewModel.lastCharacterSelected == null){
                                        val firstCharacter = adapter.snapshot().firstOrNull()
                                        if (firstCharacter != null){
                                            viewModel.lastCharacterSelected = firstCharacter
                                            previewFragment?.show(firstCharacter)
                                        }
                                    }
                                }
                            }
                        }
                    }
            }
        }
    }

    private fun onClickCharacter(character: Character) {
        viewModel.lastCharacterSelected = character
        if (binding.fragmentContainerView != null){
            previewFragment?.show(character)
        }else{
            startActivity(
                CharacterDetailActivity.buildIntent(this, character)
            )
        }
    }

    override fun showLoading(isLoading: Boolean) {
        super.showLoading(isLoading)
        binding.characterListContainer.fbScrollToTop.isVisible = !isLoading
        binding.characterListContainer.swipeRefresh.isVisible = !isLoading
    }

    override fun showPlaceholderMessage(message: String?, onRetry: (() -> Unit)?) {
        super.showPlaceholderMessage(message, onRetry)
        binding.characterListContainer.swipeRefresh.isVisible = false
    }

}