package app.githubservice.datasource.repository

import app.githubservice.datasource.remote.GithubRetrofitApi

class GithubRepository(private val githubApi: GithubRetrofitApi) {
    suspend fun getRepositories(
        query: String,
        page: Int,
        pageSize: Int
    ) = githubApi.getRepositories(query, page, pageSize)
}
