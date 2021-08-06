package app.githubservice.ui.search

import androidx.lifecycle.*
import androidx.paging.cachedIn
import app.githubservice.datasource.repository.GithubPagingRepository

const val INITIAL_SEARCH_TERM = "Koin"

object GithubSearch {
    const val BEST_MATCH = "bestmatch"
    const val STARS = "stars"
    const val FORKS = "forks"
    const val UPDATED = "updated"
}

class SearchViewModel(
    private val githubPagingRepository: GithubPagingRepository
) : ViewModel() {

    private val _currentQuery = MutableLiveData<String>()
    private val _currentSort = MutableLiveData(GithubSearch.BEST_MATCH)
    val currentSort: LiveData<String> = _currentSort

    private val combinedValues = MediatorLiveData<Pair<String?, String?>>().apply {
        addSource(_currentQuery) {
            value = Pair(it, _currentSort.value)
        }
        addSource(currentSort) {
            value = Pair(_currentQuery.value, it)
        }
    }

    val githubSearchResults = Transformations.switchMap(combinedValues) { pair ->
        val query = pair.first
        val sortBy = pair.second
        if (query != null && sortBy != null) {
            githubPagingRepository.getSearchResults(query, sortBy).cachedIn(viewModelScope)
        } else null
    }

    fun searchGithub(query: String) {
        _currentQuery.value = query
    }

    fun sortBy(sortBy: String) {
        _currentSort.value = sortBy
    }
}