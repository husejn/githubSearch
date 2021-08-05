package app.githubservice.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class GithubRepositoryResponseModel(
    @SerializedName("total_count") val totalCount: Int,
    @SerializedName("incomplete_results") val incompleteResults: Boolean,
    val items: List<GithubRepositoryModel>,
)
