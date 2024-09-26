package com.example.pinappchallenge.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pinappchallenge.core.MyApplication
import com.example.pinappchallenge.data.models.Comment
import com.example.pinappchallenge.data.models.DataComments
import com.example.pinappchallenge.databinding.FragmentCommentsBinding
import com.example.pinappchallenge.domain.usecase.GetAllCommentsUseCase
import com.example.pinappchallenge.helpers.Constants
import com.example.pinappchallenge.helpers.Constants.ID_POST
import com.example.pinappchallenge.helpers.hide
import com.example.pinappchallenge.helpers.show
import com.example.pinappchallenge.presentation.adapter.CommentsAdapter
import com.example.pinappchallenge.presentation.viewmodels.CommentViewModel
import javax.inject.Inject

/**
 * @author Axel Sanchez
 */
class CommentsFragment : Fragment() {

    private var idPost: Int = 0

    @Inject
    lateinit var getAllCommentsUseCase: GetAllCommentsUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().application as MyApplication).component.inject(this)
    }

    private val viewModel: CommentViewModel by viewModels(
        factoryProducer = { CommentViewModel.CommentViewModelFactory(getAllCommentsUseCase) }
    )

    private var fragmentCommentsBinding: FragmentCommentsBinding? = null
    private val binding get() = fragmentCommentsBinding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        fragmentCommentsBinding = FragmentCommentsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentCommentsBinding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        idPost = arguments?.getInt(ID_POST) ?: 0

        viewModel.getComments(idPost)

        viewModel.getCommentsLiveData().observe(viewLifecycleOwner) { comments ->
            updateView(comments)
        }
    }

    private fun updateView(dataComments: DataComments) {
        with(binding) {

            dataComments.results?.let { comments ->
                if (comments.isEmpty()) {
                    rvComments.hide()
                    tvErrorText.text = Constants.ApiError.EMPTY_CHARACTERS.error
                    cvEmptyState.show()
                } else {
                    rvComments.show()
                    setAdapter(comments)
                }
            }?: kotlin.run {
                tvErrorText.text = dataComments.apiError?.error
                cvEmptyState.show()
                rvComments.hide()
            }
            cpiLoading.hide()
        }
    }

    private fun setAdapter(posts: List<Comment?>) {
        val postAdapter = CommentsAdapter(posts)
        with(binding.rvComments) {
            layoutManager = LinearLayoutManager(context)
            adapter = postAdapter
        }
    }
}