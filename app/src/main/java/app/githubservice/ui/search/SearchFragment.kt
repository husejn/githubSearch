package app.githubservice.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import app.githubservice.databinding.SearchFragmentBinding
import app.githubservice.model.GithubRepositoryModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

const val DEFAULT_DEBOUNCE_DELAY = 1000L

class SearchFragment : Fragment(), SearchAdapter.OnItemClickListener {
    private val viewModel: SearchViewModel by viewModel()
    private var _binding: SearchFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        SearchFragmentBinding.inflate(inflater, container, false).apply {
            _binding = this
            viewModel = this@SearchFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
            return root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = SearchAdapter(this)

        binding.recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
            header = GithubLoadStateAdapter { adapter.retry() },
            footer = GithubLoadStateAdapter { adapter.retry() }
        )
        binding.buttonTryAgain.setOnClickListener { adapter.retry() }

        adapter.addLoadStateListener { loadState ->
            binding.apply {
                binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                binding.recyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
                binding.buttonTryAgain.isVisible = loadState.source.refresh is LoadState.Error
                binding.textViewError.isVisible = loadState.source.refresh is LoadState.Error

                if (
                    loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    adapter.itemCount < 1
                ) {
                    recyclerView.isVisible = false
                    textViewNoResults.isVisible = true
                } else {
                    textViewNoResults.isVisible = false
                }
            }
        }

        viewModel.githubSearchResults.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.searchGithub(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                searchDebounced(newText)
                return false
            }
        })

        binding.searchView.setQuery(INITIAL_SEARCH_TERM, true)

    }

    private var searchJob: Job? = null

    fun searchDebounced(searchText: String) {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            delay(DEFAULT_DEBOUNCE_DELAY)
            viewModel.searchGithub(searchText)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(githubRepo: GithubRepositoryModel) {
        Toast.makeText(requireContext(), githubRepo.fullName, Toast.LENGTH_SHORT).show()
    }

}