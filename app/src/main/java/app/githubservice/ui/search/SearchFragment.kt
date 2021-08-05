package app.githubservice.ui.search

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import app.githubservice.databinding.ItemGithubSearchBinding
import app.githubservice.databinding.SearchFragmentBinding
import app.githubservice.model.GithubRepository
import app.githubservice.model.base.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {
    private val viewModel: SearchViewModel by viewModel()
    private var _binding: SearchFragmentBinding? = null
    private val binding get() = _binding!!
    private val searchAdapter = SearchAdapter(emptyList())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        SearchFragmentBinding.inflate(inflater, container, false).apply {
            _binding = this
            viewModel = this@SearchFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
            return root
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    @FlowPreview
    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.adapter = searchAdapter

        viewModel.searchResult.observe(viewLifecycleOwner) { it ->


            when (it) {
                is Resource.Success -> {
                    it.data.items.let {
                        searchAdapter.list = it
                        searchAdapter.notifyDataSetChanged()

                        binding.textViewError.visibility = View.GONE
                    }
                }
                is Resource.Error -> {
                    searchAdapter.list = emptyList()
                    searchAdapter.notifyDataSetChanged()
                    binding.textViewError.visibility = View.VISIBLE
                    binding.textViewError.text = it.exception.localizedMessage
                }
                else -> {
                }
            }
        }


        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                lifecycleScope.launch {
                    viewModel.queryChannel.send(newText)
                }
                return false
            }
        })


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    class SearchAdapter(var list: List<GithubRepository>) :
        RecyclerView.Adapter<SearchViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemGithubSearchBinding.inflate(layoutInflater, parent, false)
            return SearchViewHolder(binding)
        }

        override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
            holder.bind(list[position])
        }

//        companion object {
//            private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<String>() {
//                override fun areItemsTheSame(oldItem: String, newItem: String): Boolean =
//                    oldItem == newItem
//
//                override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
//                    oldItem == newItem
//            }
//        }

        override fun getItemCount(): Int {
            return list.size
        }
    }

    class SearchViewHolder(private val binding: ItemGithubSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(githubRepo: GithubRepository) {
            binding.textViewUserName.text = githubRepo.fullName
        }
    }

}