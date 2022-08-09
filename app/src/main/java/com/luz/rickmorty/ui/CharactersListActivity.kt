package com.luz.rickmorty.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
import com.luz.rickmorty.data.network.parseError
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

    override val onRetry: (() -> Unit)
        get() = { adapter.refresh() }

    private val adapter : CharactersAdapter by lazy { CharactersAdapter(this::onClickCharacter) }

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
                    //        header.loadState = loadStates.refresh
                    //        footer.loadState = loadStates.append
                    adapter.loadStateFlow.collect{ loadStates ->
                        Log.i("LOAD_STATES", "$loadStates" )
                        when(loadStates.refresh){
                            is LoadState.Loading -> showLoading(true)
                            is LoadState.Error -> {
                                //si hubo error no para por notLoading
                                val error = (loadStates.refresh as LoadState.Error).error
                                showPlaceholderMessage(error.parseError(context)){ adapter.refresh() }
                            }
                            is LoadState.NotLoading -> {
                                showLoading(false)
                            }
                        }
                        binding.swipeRefresh.isRefreshing = loadStates.mediator?.refresh is LoadState.Loading
                    }
                }
            }
        }
    }

    private fun onClickCharacter(character: Character) {
        startActivity(
            CharacterDetailActivity.buildIntent(this, character)
        )
    }

    override fun showLoading(isLoading: Boolean) {
        super.showLoading(isLoading)
        binding.swipeRefresh.isVisible = !isLoading
    }

    override fun showPlaceholderMessage(message: String?, onRetry: (() -> Unit)?) {
        super.showPlaceholderMessage(message, onRetry)
        binding.swipeRefresh.isVisible = false
    }

}