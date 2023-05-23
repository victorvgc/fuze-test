package com.victorvgc.cstv.home.ui

import android.content.Context
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.victorvgc.cstv.R
import com.victorvgc.cstv.core.ui.theme.BaseActivity
import com.victorvgc.cstv.core.ui.theme.CSTVTheme
import com.victorvgc.cstv.core.ui.theme.White
import com.victorvgc.cstv.home.domain.model.Match
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = getColor(R.color.primary)
        window.navigationBarColor = getColor(R.color.primary)

        setContent {
            val matches = viewModel.matchesFlow.collectAsLazyPagingItems()

            CSTVTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    when (val state = matches.loadState.refresh) {
                        is LoadState.Error -> PageError(msg = state.error.message)
                        LoadState.Loading -> PageLoading()

                        else -> HomeScreen(context = this, matches)
                    }
                }
            }
        }
    }

    @Composable
    fun HomeScreen(context: Context, matches: LazyPagingItems<Match>) {
        Column {
            Text(
                modifier = Modifier.padding(24.dp),
                text = context.getString(R.string.matches),
                style = MaterialTheme.typography.headlineLarge
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                when (val state = matches.loadState.prepend) {
                    is LoadState.Error -> error(state.error.message ?: "")
                    LoadState.Loading -> loading()
                    is LoadState.NotLoading -> Unit
                }

                when (val state = matches.loadState.refresh) {
                    is LoadState.Error -> error(state.error.message ?: "")
                    LoadState.Loading -> loading()
                    is LoadState.NotLoading -> Unit
                }

                items(count = matches.itemCount, key = { it }, contentType = { matches[it] }) {
                    val match = matches[it]
                    if (match != null && match.opponents.size == 2) {
                        MatchItem(context = context, match = match)
                    }
                }

                when (val state = matches.loadState.append) {
                    is LoadState.Error -> error(state.error.message ?: "")
                    LoadState.Loading -> loading()
                    is LoadState.NotLoading -> Unit
                }
            }
        }
    }

    private fun LazyListScope.loading() {
        item {
            CircularProgressIndicator(modifier = Modifier.padding(16.dp), color = White)
        }
    }

    private fun LazyListScope.error(msg: String) {
        item {
            Text(
                text = msg,
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.tertiary
            )
        }
    }
}

