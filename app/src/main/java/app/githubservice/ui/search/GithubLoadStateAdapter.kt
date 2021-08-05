package app.githubservice.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import app.githubservice.databinding.RecyclerViewFooterBinding


class GithubLoadStateAdapter(private val tryAgainCallback: () -> Unit) :
    LoadStateAdapter<GithubLoadStateAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
        val binding = RecyclerViewFooterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    inner class ViewHolder(private val binding: RecyclerViewFooterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.buttonTryAgain.setOnClickListener {
                tryAgainCallback.invoke()
            }
        }

        fun bind(loadState: LoadState) {
            binding.apply {
                progressBar.isVisible = loadState is LoadState.Loading
                buttonTryAgain.isVisible = loadState !is LoadState.Loading
                textViewError.isVisible = loadState !is LoadState.Loading
            }
        }
    }
}