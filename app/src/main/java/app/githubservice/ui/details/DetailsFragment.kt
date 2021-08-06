package app.githubservice.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import app.githubservice.databinding.DetailsFragmentBinding
import app.githubservice.model.GithubRepositoryModel

class DetailsFragment : Fragment() {
    private var _binding: DetailsFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var githubRepo: GithubRepositoryModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        DetailsFragmentBinding.inflate(inflater, container, false).apply {
            _binding = this
            lifecycleOwner = viewLifecycleOwner
            return root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        githubRepo = DetailsFragmentArgs.fromBundle(requireArguments()).githubRepositoryModel

        Toast.makeText(requireContext(), githubRepo.fullName, Toast.LENGTH_SHORT).show()

    }
}