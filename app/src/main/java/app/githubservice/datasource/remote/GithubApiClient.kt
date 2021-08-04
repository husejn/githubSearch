package app.githubservice.datasource.remote

import app.githubservice.model.GithubRepositoryResponseModel
import app.githubservice.model.base.Resource


interface GithubApiClient {
    suspend fun getRepositories(
        query: String,
        page: Int,
        pageSize: Int
    ): Resource<GithubRepositoryResponseModel>
}
