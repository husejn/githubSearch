package app.githubservice.datasource.repository

import androidx.paging.*
import app.githubservice.datasource.GithubPagingSource
import app.githubservice.datasource.remote.GithubRetrofitApi


class GithubPagingRepository constructor(private val githubApi: GithubRetrofitApi) {
    fun getSearchResults(query: String) =
        Pager(
            config = PagingConfig(
                pageSize = 25,
            ),
            pagingSourceFactory = { GithubPagingSource(githubApi, query) }
        ).liveData
}
