package com.example.pinappchallenge.presentation.ui.compose

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
import com.example.pinappchallenge.helpers.Constants
import com.example.pinappchallenge.R
import com.example.pinappchallenge.data.models.DataComments
import com.example.pinappchallenge.presentation.viewmodels.CommentViewModel

/**
 * @author Axel Sanchez
 */

@Composable
fun CommentsScreen(
    idPost: Int, viewModel: CommentViewModel
) {
    viewModel.getComments(idPost)
    val dataComments: DataComments by viewModel.getCommentsLiveData()
        .observeAsState(initial = DataComments())


    DisposableEffect(dataComments) {
        onDispose {
            viewModel.reset()
        }
    }


    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val (emptyState, loading) = createRefs()

        ErrorState(modifier = Modifier.constrainAs(emptyState) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }, dataComments)

        Loading(modifier = Modifier.constrainAs(loading) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }, dataComments)

        CommentsList(dataComments)
    }
}

@Composable
private fun Loading(modifier: Modifier, dataComments: DataComments) {
    if (dataComments.results == null) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun CommentsList(dataComments: DataComments) {
    if (!dataComments.results.isNullOrEmpty()) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            itemsIndexed(dataComments.results) { index, comment ->
                ConstraintLayout(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val (name, email, body, divider) = createRefs()
                    Text(
                        modifier = Modifier
                            .constrainAs(name) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                width = Dimension.fillToConstraints
                            }
                            .padding(top = 20.dp, end = 20.dp, start = 20.dp),
                        text = comment?.name ?: "",
                        softWrap = true,
                        fontSize = 25.sp,
                        color = colorResource(id = R.color.black),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        modifier = Modifier
                            .constrainAs(email) {
                                top.linkTo(name.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                width = Dimension.fillToConstraints
                            }
                            .padding(top = 20.dp, end = 20.dp, start = 20.dp),
                        text = "$${comment?.email.toString()}",
                        softWrap = true,
                        color = colorResource(id = R.color.black),
                        textAlign = TextAlign.Center
                    )

                    Text(
                        modifier = Modifier
                            .constrainAs(body) {
                                top.linkTo(email.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                width = Dimension.fillToConstraints
                            }
                            .padding(top = 20.dp, end = 20.dp, start = 20.dp),
                        text = "$${comment?.body.toString()}",
                        softWrap = true,
                        color = colorResource(id = R.color.black),
                        textAlign = TextAlign.Center
                    )

                    if (index != dataComments.results.size - 1) {
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
private fun ErrorState(modifier: Modifier, dataComments: DataComments) {
    dataComments.results?.let { products ->
        if (products.isEmpty()) {
            ErrorCard(Constants.ApiError.EMPTY_POSTS.error, modifier)
        }
    } ?: run {
        dataComments.apiError?.let {
            ErrorCard(it.error, modifier)
        }
    }
}
