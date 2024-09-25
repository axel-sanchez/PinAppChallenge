package com.example.pinappchallenge.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pinappchallenge.R
import com.example.pinappchallenge.core.MyApplication
import com.example.pinappchallenge.data.models.DataPost
import com.example.pinappchallenge.data.models.Post
import com.example.pinappchallenge.databinding.FragmentPostsBinding
import com.example.pinappchallenge.domain.usecase.GetAllPostsUseCase
import com.example.pinappchallenge.helpers.Constants
import com.example.pinappchallenge.helpers.Constants.ID_POST
import com.example.pinappchallenge.helpers.hide
import com.example.pinappchallenge.helpers.show
import com.example.pinappchallenge.presentation.adapter.PostAdapter
import com.example.pinappchallenge.presentation.viewmodels.PostViewModel
import javax.inject.Inject

/**
 * @author Axel Sanchez
 */
class PostsFragment: Fragment() {

    @Inject
    lateinit var getAllPostsUseCase: GetAllPostsUseCase

    private var fragmentPostsBinding: FragmentPostsBinding? = null
    private val binding get() = fragmentPostsBinding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        fragmentPostsBinding = FragmentPostsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentPostsBinding = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().application as MyApplication).component.inject(this)
    }

    private val viewModel: PostViewModel by viewModels(
        factoryProducer = { PostViewModel.PostViewModelFactory(getAllPostsUseCase) }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getPosts()

        viewModel.getPostsLiveData().observe(viewLifecycleOwner) { response ->
            updateView(response)
        }
    }

    private fun updateView(dataPost: DataPost) {
        with(binding) {

            dataPost.results?.let { characters ->
                if (characters.isEmpty()) {
                    rvPosts.hide()
                    tvErrorText.text = Constants.ApiError.EMPTY_CHARACTERS.error
                    cvEmptyState.show()
                } else {
                    rvPosts.show()
                    setAdapter(characters)
                }
            }?: kotlin.run {
                tvErrorText.text = dataPost.apiError?.error
                cvEmptyState.show()
                rvPosts.hide()
            }
            cpiLoading.hide()
        }
    }

    private fun setAdapter(posts: List<Post?>) {
        val postAdapter = PostAdapter(posts, itemClick)
        with(binding.rvPosts) {
            layoutManager = LinearLayoutManager(context)
            adapter = postAdapter
        }
    }

    private val itemClick = { character: Post? ->
        character?.let {
            val bundle = bundleOf(ID_POST to it.id)

            //findNavController().navigate(R.id.action_charactersFragment_to_detailsFragment, bundle, null)
        }
    }
}