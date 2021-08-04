package app.githubservice.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import java.util.*


@Keep
data class GithubRepository(
    val id: Int,
    val name: String,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("html_url")
    val htmlUrl: String,
    val description: String,
    @SerializedName("watchers_count")
    val watchersCount: Int,
    @SerializedName("forks_count")
    val forksCount: Int,
    @SerializedName("open_issues_count")
    val openIssuesCount: Int,
    val language: String,
    @SerializedName("created_at")
    val createdAt: Date,
    @SerializedName("updated_at")
    val updatedAt: Date,
    val owner: GithubOwner,
)

@Keep
data class GithubOwner(
    val login: String,
    @SerializedName("avatar_url")
    val avatarUrl: String,
)