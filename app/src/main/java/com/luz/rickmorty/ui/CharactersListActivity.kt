package com.luz.rickmorty.ui

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.luz.rickmorty.data.model.Character
import com.luz.rickmorty.data.network.Result
import com.luz.rickmorty.data.repository.CharacterRepository
import com.luz.rickmorty.databinding.ActivityCharactersListBinding
import com.luz.rickmorty.databinding.PartialPlaceholderBinding
import com.luz.rickmorty.ui.adapters.CharactersAdapter
import com.luz.rickmorty.ui.adapters.CharactersLoadStateAdapter
import com.luz.rickmorty.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Luz on 3/8/2022.
 */
@AndroidEntryPoint
class CharactersListActivity : BaseActivity() {

    private lateinit var binding: ActivityCharactersListBinding

    override val partialPlaceholderBinding: PartialPlaceholderBinding
        get() = PartialPlaceholderBinding.bind(binding.placeholderContainer.root)

    private val adapter : CharactersAdapter by lazy { CharactersAdapter(this::onClickCharacter) }

    @Inject
    lateinit var characterRepository: CharacterRepository

    private val viewModel: CharacterListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharactersListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAdapter()
        initSwipeToRefresh()

        showLoading(false)
    }

    private fun initSwipeToRefresh(){
        binding.swipeRefresh.setOnRefreshListener {
            adapter.refresh()
        }
    }

    private fun initFloatingActionButton() {
        with(binding) {
            fbScrollToTop.isVisible = true
            fbScrollToTop.setOnClickListener {
                rvCharacters.smoothScrollToPosition(0)
            }
        }
    }

    private fun initAdapter(){
        with(binding) {
            rvCharacters.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            rvCharacters.addItemDecoration(
                DividerItemDecoration(
                    context,
                    LinearLayoutManager.HORIZONTAL
                )
            )
            rvCharacters.adapter = adapter.withLoadStateFooter(CharactersLoadStateAdapter(adapter))

            lifecycleScope.launch {
                //whenCreated {  }
                viewModel.myFlow.collectLatest { pagingData ->
                    adapter.submitData(pagingData)
                }
            }

            lifecycleScope.launch {
                whenCreated {
                    adapter.loadStateFlow.collect{ loadStates ->
                        Log.i("LOAD_STATES", "$loadStates " )
                        binding.swipeRefresh.isRefreshing = loadStates.mediator?.refresh is LoadState.Loading
                    }
                }
            }

            /*
            lifecycleScope.launchWhenCreated {
                adapter.loadStateFlow
                    //.asMergedLoadStates()
                    .distinctUntilChangedBy { it.refresh }
                    .filter { it.refresh is LoadState.NotLoading }
                    .collect{ binding.rvCharacters.scrollToPosition(0) }
            }*/
        }
    }

    private fun onClickCharacter(character: Character) {

    }

    override fun showLoading(isLoading: Boolean) {
        super.showLoading(isLoading)
        binding.rvCharacters.isVisible = !isLoading
    }

    override fun showPlaceholderMessage(message: String?) {
        super.showPlaceholderMessage(message)
        binding.rvCharacters.isVisible = false
    }

}