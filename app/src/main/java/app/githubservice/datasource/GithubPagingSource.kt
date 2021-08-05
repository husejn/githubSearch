package app.githubservice.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import app.githubservice.datasource.remote.GithubRetrofitApi
import app.githubservice.model.GithubRepositoryModel
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1

class GithubPagingSource(
    private val githubApi: GithubRetrofitApi,
    private val query: String
) : PagingSource<Int, GithubRepositoryModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GithubRepositoryModel> {
        val position = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response = githubApi.getRepositories(query, position, params.loadSize)

            LoadResult.Page(
                data = response.items,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (response.totalCount == 0) null else position + 1
            )

        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, GithubRepositoryModel>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}