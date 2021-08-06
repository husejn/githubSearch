package app.githubservice.ui.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import app.githubservice.R
import app.githubservice.databinding.DetailsFragmentBinding
import app.githubservice.model.GithubRepositoryModel
import app.githubservice.ui.util.openUrlLink
import com.bumptech.glide.Glide
import java.text.DateFormat
import java.util.*

class DetailsFragment : Fragment() {
    private var _binding: DetailsFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var githubRepoModel: GithubRepositoryModel

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

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        githubRepoModel = DetailsFragmentArgs.fromBundle(requireArguments()).githubRepositoryModel

        val format: DateFormat = DateFormat.getDateTimeInstance(
            DateFormat.DEFAULT,
            DateFormat.SHORT,
            Locale.getDefault()
        )

        binding.apply {
            Glide.with(imageView)
                .load(githubRepoModel.owner.avatarUrl)
                .error(R.drawable.ic_error)
                .into(imageView)

            textName.text = githubRepoModel.name
            textAuthor.text = "by ${githubRepoModel.owner.login}"
            textViewCreated.text = format.format(githubRepoModel.createdAt)
            textViewUpdated.text = format.format(githubRepoModel.updatedAt)
            textDescription.text = githubRepoModel.description

            textViewLanguage.text = githubRepoModel.language

            buttonUser.setOnClickListener {
                openUrlLink(requireContext(), githubRepoModel.owner.htmlUrl)
            }

            buttonRepository.setOnClickListener {
                openUrlLink(requireContext(), githubRepoModel.htmlUrl)
            }
        }

    }
}