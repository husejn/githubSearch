package app.githubservice.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Keep
data class GithubRepositoryModel(
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
): Parcelable

@Parcelize
@Keep
data class GithubOwner(
    val login: String,
    @SerializedName("html_url")
    val htmlUrl: String,
    @SerializedName("avatar_url")
    val avatarUrl: String,
): Parcelable