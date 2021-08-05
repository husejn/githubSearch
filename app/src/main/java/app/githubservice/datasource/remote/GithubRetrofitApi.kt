package app.githubservice.datasource.remote

import app.githubservice.model.GithubRepositoryResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubRetrofitApi {
    @GET("search/repositories?q=repos:>1")
    suspend fun getRepositories(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") pageSize: Int
    ): GithubRepositoryResponseModel

}
