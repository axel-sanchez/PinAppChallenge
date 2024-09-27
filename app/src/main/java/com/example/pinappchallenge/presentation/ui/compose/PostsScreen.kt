package com.example.pinappchallenge.presentation.ui.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.pinappchallenge.data.models.DataPosts
import com.example.pinappchallenge.helpers.Constants
import com.example.pinappchallenge.presentation.viewmodels.PostViewModel
import com.example.pinappchallenge.R

/**
 * @author Axel Sanchez
 */

@Composable
fun PostsScreen(
    viewModel: PostViewModel, navigateCommentsScreen: (Int) -> Unit
) {
    val dataPosts: DataPosts by viewModel.getPostsLiveData()
        .observeAsState(initial = DataPosts())

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val (emptyState, loading) = createRefs()

        ErrorState(modifier = Modifier.constrainAs(emptyState) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }, dataPosts)

        Loading(modifier = Modifier.constrainAs(loading) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }, dataPosts)

        PostList(dataPosts, navigateCommentsScreen)
    }
}

@Composable
private fun Loading(modifier: Modifier, dataProducts: DataPosts) {
    if (dataProducts.results == null) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun PostList(dataPosts: DataPosts, navigateCommentsScreen: (Int) -> Unit) {
    if (!dataPosts.results.isNullOrEmpty()) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            itemsIndexed(dataPosts.results) { index, post ->
                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navigateCommentsScreen(post?.id ?: 0)
                        }) {
                    val (title, body, divider) = createRefs()
                    Text(
                        modifier = Modifier
                            .constrainAs(title) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                width = Dimension.fillToConstraints
                            }
                            .padding(top = 20.dp, end = 20.dp, start = 20.dp),
                        text = post?.title ?: "",
                        softWrap = true,
                        fontSize = 25.sp,
                        color = colorResource(id = R.color.black),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        modifier = Modifier
                            .constrainAs(body) {
                                top.linkTo(title.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                width = Dimension.fillToConstraints
                            }
                            .padding(top = 20.dp, end = 20.dp, start = 20.dp),
                        text = post?.body ?: "",
                        softWrap = true,
                        color = colorResource(id = R.color.black),
                        textAlign = TextAlign.Center
                    )

                    if (index != dataPosts.results.size - 1) {
                        Divider(
                            modifier = Modifier
                                .constrainAs(divider) {
                                    top.linkTo(body.bottom)
                                }
                                .padding(top = 20.dp),
                            color = colorResource(id = R.color.separator_gray),
                            thickness = 1.dp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ErrorState(modifier: Modifier, dataPosts: DataPosts) {
    dataPosts.results?.let { products ->
        if (products.isEmpty()) {
            ErrorCard(Constants.ApiError.EMPTY_POSTS.error, modifier)
        }
    } ?: run {
        dataPosts.apiError?.let {
            ErrorCard(it.error, modifier)
        }
    }
}
