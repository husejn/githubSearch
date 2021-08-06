package app.githubservice.ui.search

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import app.githubservice.R
import app.githubservice.databinding.ItemGithubSearchBinding
import app.githubservice.model.GithubRepositoryModel
import com.bumptech.glide.Glide


class SearchAdapter(private val listener: OnItemClickListener) :
    PagingDataAdapter<GithubRepositoryModel, SearchAdapter.SearchViewHolder>(ITEM_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding =
            ItemGithubSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    inner class SearchViewHolder(private val binding: ItemGithubSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if (item != null) {
                        listener.onItemClick(item)
                    }
                }
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(githubRepoModel: GithubRepositoryModel) {
            binding.apply {
                githubRepoModel.apply {
                    Glide.with(itemView)
                        .load(githubRepoModel.owner.avatarUrl)
                        .error(R.drawable.ic_error)
                        .into(imageView)
                    textViewRepositoryFullName.text = "$name by ${owner.login}"
                    textViewRepositoryForks.text = forksCount.toString()
                    textViewRepositoryOpenIssues.text = openIssuesCount.toString()
                    textViewRepositoryWatchers.text = watchersCount.toString()
                }

            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(githubRepo: GithubRepositoryModel)
    }

    companion object {
        private val ITEM_COMPARATOR = object : DiffUtil.ItemCallback<GithubRepositoryModel>() {
            override fun areItemsTheSame(
                oldItem: GithubRepositoryModel,
                newItem: GithubRepositoryModel
            ) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: GithubRepositoryModel,
                newItem: GithubRepositoryModel
            ) =
                oldItem == newItem
        }
    }
}