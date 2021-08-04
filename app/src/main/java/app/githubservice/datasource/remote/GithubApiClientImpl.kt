package app.githubservice.datasource.remote

import app.githubservice.model.GithubRepositoryResponseModel
import app.githubservice.model.base.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException


class GithubApiClientImpl(private val githubApi: GithubRetrofitApi) : GithubApiClient {
    override suspend fun getRepositories(
        query: String,
        page: Int,
        pageSize: Int
    ): Resource<GithubRepositoryResponseModel> = withContext(Dispatchers.IO) {
        try {
            val response = githubApi.getRepositories(query, page, pageSize)
            if (response.isSuccessful) {
                Resource.Success(response.body()!!)
            } else {
                Resource.Error(HttpException(response))
            }
        } catch (ex: Throwable) {
            Resource.Error(ex)
        }
    }
}
