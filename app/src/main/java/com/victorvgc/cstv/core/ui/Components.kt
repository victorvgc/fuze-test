package com.victorvgc.cstv.core.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.victorvgc.cstv.R

@Composable
fun TeamDisplay(logo: String?, name: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(18.dp)) {
        if (logo != null) {
            AsyncImage(
                modifier = Modifier
                    .width(60.dp)
                    .height(60.dp),
                model = logo,
                contentDescription = name,
                placeholder = painterResource(id = R.drawable.team_logo_placeholder),
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.team_logo_placeholder),
                contentDescription = name
            )
        }
        Spacer(modifier = Modifier.padding(5.dp))
        Text(text = name, style = MaterialTheme.typography.bodyMedium)
    }
}