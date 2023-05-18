package com.victorvgc.cstv.home.ui

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.victorvgc.cstv.R
import com.victorvgc.cstv.core.ui.theme.CSTVTheme
import com.victorvgc.cstv.core.ui.theme.White

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = getColor(R.color.primary)
        window.navigationBarColor = getColor(R.color.primary)

        setContent {
            CSTVTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HomeScreen(context = this)
                }
            }
        }
    }
}

@Composable
fun HomeScreen(context: Context) {
    Column {
        Text(
            modifier = Modifier.padding(24.dp),
            text = context.getString(R.string.matches),
            style = MaterialTheme.typography.headlineLarge
        )
        MatchItem(context)
    }
}