package app.githubservice.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import app.githubservice.databinding.SearchFragmentBinding

class SearchFragment : Fragment() {
    private val viewModel by viewModels<SearchViewModel>()
    private var binding: SearchFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        SearchFragmentBinding.inflate(inflater, container, false).apply {
            binding = this
            viewModel = this@SearchFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
            return root
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}