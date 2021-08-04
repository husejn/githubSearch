package app.githubservice.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import app.githubservice.databinding.SearchFragmentBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SearchFragment : Fragment() {
    private val viewModel: SearchViewModel by sharedViewModel()
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