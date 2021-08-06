package app.githubservice.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import app.githubservice.databinding.DetailsFragmentBinding

class DetailsFragment : Fragment() {
    private var _binding: DetailsFragmentBinding? = null
    private val binding get() = _binding!!

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
}