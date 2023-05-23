package com.victorvgc.cstv.details.ui

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.victorvgc.cstv.R
import com.victorvgc.cstv.core.domain.model.Player
import com.victorvgc.cstv.core.ui.BaseActivity
import com.victorvgc.cstv.core.ui.TeamDisplay
import com.victorvgc.cstv.core.ui.theme.CSTVTheme
import com.victorvgc.cstv.core.ui.theme.GreyDark
import com.victorvgc.cstv.core.ui.theme.White
import com.victorvgc.cstv.core.utils.DateType
import com.victorvgc.cstv.core.utils.getDateType
import com.victorvgc.cstv.core.utils.toDate
import com.victorvgc.cstv.core.utils.toDateString
import com.victorvgc.cstv.details.domain.model.Details
import com.victorvgc.cstv.details.domain.model.DetailsState
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.min

@AndroidEntryPoint
class DetailsActivity : BaseActivity() {

    companion object {
        const val EXTRAS_LEAGUE_SERIES = "leagueSeries"
        const val EXTRAS_MATCH_DATE = "matchDate"
        const val EXTRAS_TEAM_1_SLUG = "team1"
        const val EXTRAS_TEAM_2_SLUG = "team2"
    }

    private val viewModel: DetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val leagueSeries = intent.getStringExtra(EXTRAS_LEAGUE_SERIES)
        val matchDate = intent.getStringExtra(EXTRAS_MATCH_DATE)
        val team1 = intent.getStringExtra(EXTRAS_TEAM_1_SLUG)
        val team2 = intent.getStringExtra(EXTRAS_TEAM_2_SLUG)

        setContent {
            CSTVTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val state = viewModel.detailsFlow.collectAsState()

                    when (val detailsState = state.value) {
                        is DetailsState.Error -> PageError(msg = detailsState.msg)
                        DetailsState.Loading -> PageLoading()
                        is DetailsState.Success -> DetailsScreen(this, detailsState.data)
                    }
                }
            }
        }

        if (leagueSeries.isNullOrEmpty() ||
            team1.isNullOrEmpty() ||
            team2.isNullOrEmpty()
        ) {
            Toast.makeText(this, getString(R.string.error_empty_data), Toast.LENGTH_SHORT).show()

            finish()
        }

        viewModel.setData(
            leagueSerieName = leagueSeries!!,
            team1Slug = team1!!,
            team2Slug = team2!!,
            matchDate = matchDate
        )
    }

    @Composable
    private fun DetailsScreen(context: Context, details: Details) {
        Column(modifier = Modifier.fillMaxSize()) {
            ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
                val (back, title) = createRefs()

                Image(
                    modifier = Modifier
                        .padding(horizontal = 24.dp, vertical = 45.dp)
                        .width(16.dp)
                        .height(16.dp)
                        .constrainAs(back) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                        }
                        .clickable { finish() },
                    painter = painterResource(id = R.drawable.ic_back_arrow),
                    contentDescription = context.getString(
                        R.string.back
                    )
                )
                Text(
                    modifier = Modifier
                        .padding(vertical = 40.dp)
                        .constrainAs(title) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            width = Dimension.fillToConstraints
                        },
                    text = details.leagueSeriesName,
                    style = MaterialTheme.typography.headlineMedium.copy(textAlign = TextAlign.Center)
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TeamDisplay(
                    logo = details.opponents[0].imageUrl,
                    name = details.opponents[0].name ?: ""
                )
                Spacer(modifier = Modifier.padding(5.dp))
                Text(
                    text = context.getString(R.string.versus),
                    style = MaterialTheme.typography.labelMedium
                )
                Spacer(modifier = Modifier.padding(5.dp))
                TeamDisplay(
                    logo = details.opponents[1].imageUrl,
                    name = details.opponents[1].name ?: ""
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                val bgColor = if (details.matchDate.toDate().getDateType() == DateType.NOW) {
                    MaterialTheme.colorScheme.tertiary
                } else {
                    MaterialTheme.colorScheme.background
                }

                Card(
                    colors = CardDefaults.cardColors(containerColor = bgColor),
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        text = details.matchDate.toDateString(this@DetailsActivity),
                        style = MaterialTheme.typography.labelMedium,
                        color = White
                    )
                }
            }

            val playerSize =
                min(details.opponents[0].players.size, details.opponents[1].players.size)

            LazyColumn() {
                items(count = playerSize,
                    key = { it },
                    contentType = { details.opponents }
                ) {
                    PlayerLine(
                        player1 = details.opponents[0].players[it],
                        player2 = details.opponents[1].players[it]
                    )
                }
            }
        }
    }

    @Composable
    private fun PlayerLine(player1: Player, player2: Player) {
        ConstraintLayout(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)) {
            val (bgLeft, imgLeft, bgRight, imgRight) = createRefs()

            Card(
                modifier = Modifier
                    .fillMaxWidth(.5f)
                    .padding(end = 8.dp)
                    .constrainAs(bgLeft) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        width = Dimension.fillToConstraints
                    },
                shape = RoundedCornerShape(
                    topStart = 0.dp,
                    bottomStart = 0.dp,
                    topEnd = 12.dp,
                    bottomEnd = 12.dp
                ),
                colors = CardDefaults.cardColors(containerColor = GreyDark)
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 90.dp, top = 16.dp),
                    text = player1.nickname,
                    style = MaterialTheme.typography.displayMedium.copy(textAlign = TextAlign.End),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 90.dp, bottom = 6.dp),
                    text = player1.fullName,
                    style = MaterialTheme.typography.displaySmall.copy(textAlign = TextAlign.End),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            val imgLeftModifier = Modifier
                .constrainAs(imgLeft) {
                    end.linkTo(bgLeft.end)
                    bottom.linkTo(bgLeft.bottom)
                }
                .padding(bottom = 8.dp, end = 18.dp)
                .width(64.dp)
                .height(64.dp)
                .clip(shape = RoundedCornerShape(8.dp))

            if (player1.imageUrl.isNullOrEmpty()) {
                Image(
                    modifier = imgLeftModifier,
                    painter = painterResource(id = R.drawable.player_placeholder),
                    contentDescription = player2.nickname
                )
            } else {
                AsyncImage(
                    modifier = imgLeftModifier,
                    model = player1.imageUrl,
                    contentDescription = player2.nickname,
                    placeholder = painterResource(
                        id = R.drawable.player_placeholder
                    ),
                    contentScale = ContentScale.Crop
                )
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth(.5f)
                    .padding(start = 8.dp)
                    .constrainAs(bgRight) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        width = Dimension.fillToConstraints
                    },
                shape = RoundedCornerShape(
                    topStart = 12.dp,
                    bottomStart = 12.dp,
                    topEnd = 0.dp,
                    bottomEnd = 0.dp
                ),
                colors = CardDefaults.cardColors(containerColor = GreyDark)
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 90.dp, top = 16.dp),
                    text = player2.nickname,
                    style = MaterialTheme.typography.displayMedium.copy(textAlign = TextAlign.Start),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 90.dp, bottom = 6.dp),
                    text = player2.fullName,
                    style = MaterialTheme.typography.displaySmall.copy(textAlign = TextAlign.Start),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            val imgRightModifier = Modifier
                .constrainAs(imgRight) {
                    start.linkTo(bgRight.start)
                    bottom.linkTo(bgRight.bottom)
                }
                .padding(bottom = 8.dp, start = 18.dp)
                .width(64.dp)
                .height(64.dp)
                .clip(shape = RoundedCornerShape(8.dp))

            if (player2.imageUrl.isNullOrEmpty()) {
                Image(
                    modifier = imgRightModifier,
                    painter = painterResource(id = R.drawable.player_placeholder),
                    contentDescription = player2.nickname
                )
            } else {
                AsyncImage(
                    modifier = imgRightModifier,
                    model = player2.imageUrl,
                    contentDescription = player2.nickname,
                    placeholder = painterResource(
                        id = R.drawable.player_placeholder
                    ),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}