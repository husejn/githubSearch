package app.githubservice.di

import app.githubservice.BuildConfig
import app.githubservice.datasource.repository.GithubPagingRepository
import app.githubservice.datasource.remote.GithubRetrofitApi
import app.githubservice.ui.search.SearchViewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single { provideGithubApi(get()) }
    single { provideRetrofit() }
    single { GithubPagingRepository(get()) }
    factory { SearchViewModel(get()) }
}

fun provideRetrofit(): Retrofit {
    return Retrofit.Builder().baseUrl(BuildConfig.API_URL)
        .addConverterFactory(GsonConverterFactory.create()).build()
}

fun provideGithubApi(retrofit: Retrofit): GithubRetrofitApi =
    retrofit.create(GithubRetrofitApi::class.java)
