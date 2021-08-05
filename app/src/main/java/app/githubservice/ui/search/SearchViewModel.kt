package app.githubservice.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import app.githubservice.datasource.remote.GithubApiClient
import app.githubservice.model.GithubRepositoryResponseModel
import app.githubservice.model.base.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.mapLatest


class SearchViewModel(
    private val githubApiClient: GithubApiClient
) : ViewModel() {
    val string = "Fragment"

    @ExperimentalCoroutinesApi
    val queryChannel = BroadcastChannel<String>(Channel.CONFLATED)


    @FlowPreview
    @ExperimentalCoroutinesApi
    val searchResult: LiveData<Resource<GithubRepositoryResponseModel>> = queryChannel
        .asFlow()
        .debounce(500)
        .mapLatest {
            try {
                githubApiClient.getRepositories(it, 1, 15)
            } catch (e: Throwable) {
                Resource.Error(e)
            }
        }
        .catch {
            emit(Resource.Error(it))
        }
        .asLiveData()

}