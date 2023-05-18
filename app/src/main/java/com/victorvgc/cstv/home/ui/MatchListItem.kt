package com.victorvgc.cstv.home.ui

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.victorvgc.cstv.R
import com.victorvgc.cstv.core.ui.theme.Accent
import com.victorvgc.cstv.core.ui.theme.GreyDark
import com.victorvgc.cstv.core.ui.theme.WhiteAlpha20
import com.victorvgc.cstv.home.domain.model.Match

@Composable
fun MatchItem(context: Context, match: Match) {
    Card(
        modifier = Modifier.padding(24.dp),
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
                logo = "https://seeklogo.com/images/M/mibr-logo-07C299F0C0-seeklogo.com.png",
                name = "mibr"
            )
            Spacer(modifier = Modifier.padding(5.dp))
            Text(
                text = context.getString(R.string.versus),
                style = MaterialTheme.typography.labelMedium
            )
            Spacer(modifier = Modifier.padding(5.dp))
            TeamDisplay(
                logo = "https://seeklogo.com/images/M/mibr-logo-07C299F0C0-seeklogo.com.png",
                name = "mibr"
            )
        }
        Divider(color = WhiteAlpha20, thickness = 1.dp)
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.league_logo_placeholder),
                contentDescription = "league"
            )
            Spacer(modifier = Modifier.padding(4.dp))
            Text(text = "League + series", style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
fun TeamDisplay(logo: String, name: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(18.dp)) {
        AsyncImage(
            model = logo,
            contentDescription = name,
            placeholder = painterResource(id = R.drawable.team_logo_placeholder),
        )
        Spacer(modifier = Modifier.padding(5.dp))
        Text(text = name, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun TimeDisplay(context: Context, match: Match) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Accent
        ), shape = RoundedCornerShape(bottomStart = 16.dp, topEnd = 16.dp)
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            Text(text = "AGORA", style = MaterialTheme.typography.labelSmall)
        }
    }
}