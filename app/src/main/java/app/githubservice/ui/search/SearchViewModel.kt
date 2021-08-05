package app.githubservice.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import app.githubservice.datasource.repository.GithubPagingRepository

const val INITIAL_SEARCH_TERM = "Koin"

class SearchViewModel(
    private val githubPagingRepository: GithubPagingRepository
) : ViewModel() {

    private val currentQuery = MutableLiveData("Hi")

    val githubSearchResults = currentQuery.switchMap { queryString ->
        githubPagingRepository.getSearchResults(queryString).cachedIn(viewModelScope)
    }

    fun searchGithub(query: String) {
        currentQuery.value = query
    }
}