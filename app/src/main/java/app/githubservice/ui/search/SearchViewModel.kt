package app.githubservice.ui.search

import androidx.lifecycle.ViewModel
import app.githubservice.datasource.remote.GithubApiClient


class SearchViewModel(
    private val githubApiClient: GithubApiClient
) : ViewModel() {
    val string = "Fragment"
}