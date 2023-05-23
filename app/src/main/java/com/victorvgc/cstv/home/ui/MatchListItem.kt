package com.victorvgc.cstv.home.ui

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.victorvgc.cstv.R
import com.victorvgc.cstv.core.ui.TeamDisplay
import com.victorvgc.cstv.core.ui.theme.Accent
import com.victorvgc.cstv.core.ui.theme.GreyDark
import com.victorvgc.cstv.core.ui.theme.WhiteAlpha20
import com.victorvgc.cstv.core.utils.DateType
import com.victorvgc.cstv.core.utils.getDateType
import com.victorvgc.cstv.core.utils.toDate
import com.victorvgc.cstv.core.utils.toDateString
import com.victorvgc.cstv.home.domain.model.Match

@Composable
fun MatchItem(context: Context, match: Match, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.padding(horizontal = 24.dp, vertical = 12.dp),
        colors = CardDefaults.cardColors(
            containerColor = GreyDark
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End
        ) {
            TimeDisplay(context = context, match = match)
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TeamDisplay(
                logo = match.opponents[0].imageUrl,
                name = match.opponents[0].name ?: ""
            )
            Spacer(modifier = Modifier.padding(5.dp))
            Text(
                text = context.getString(R.string.versus),
                style = MaterialTheme.typography.labelMedium
            )
            Spacer(modifier = Modifier.padding(5.dp))
            TeamDisplay(
                logo = match.opponents[1].imageUrl,
                name = match.opponents[1].name ?: ""
            )
        }
        Divider(color = WhiteAlpha20, thickness = 1.dp)
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            if (match.league.imageUrl != null) {
                AsyncImage(
                    modifier = Modifier
                        .width(16.dp)
                        .height(16.dp),
                    model = match.league.imageUrl, contentDescription = match.league.name,
                    placeholder = painterResource(id = R.drawable.league_logo_placeholder),
                    contentScale = ContentScale.FillBounds
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.league_logo_placeholder),
                    contentDescription = match.league.name
                )
            }
            Spacer(modifier = Modifier.padding(4.dp))
            Text(
                text = match.league.name + " " + match.serie.name,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
fun TimeDisplay(context: Context, match: Match) {
    val color = if (match.beginAt.toDate()
            .getDateType() == DateType.NOW || !match.live.url.isNullOrEmpty()
    ) {
        Accent
    } else {
        WhiteAlpha20
    }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = color
        ), shape = RoundedCornerShape(bottomStart = 16.dp, topEnd = 16.dp)
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            Text(
                text = match.beginAt.toDateString(context),
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}