package com.example.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artspace.ui.theme.ArtSpaceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArtSpaceTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ArtSpaceScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun ArtSpaceScreen(modifier: Modifier = Modifier) {
    var currentArtwork by remember { mutableStateOf(1) }

    val imageRes = when (currentArtwork) {
        1 -> R.drawable.golden_lilies
        2 -> R.drawable.pink_peonies
        else -> R.drawable.white_lilies
    }
    val titleRes = when (currentArtwork) {
        1 -> R.string.golden_lilies
        2 -> R.string.pink_peonies
        else -> R.string.white_lilies
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            ArtworkWall(imageRes = imageRes)
            Text(
                text = "$currentArtwork / 3",
                modifier = Modifier.padding(top = 8.dp)
            )
        }
        ArtworkDescriptor(titleRes = titleRes)
        DisplayController(
            onPreviousClick = {
                currentArtwork = if (currentArtwork == 1) 3 else currentArtwork - 1
            },
            onNextClick = {
                currentArtwork = if (currentArtwork == 3) 1 else currentArtwork + 1
            }
        )
    }
}

@Composable
fun ArtworkWall(@DrawableRes imageRes: Int, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.padding(top = 32.dp),
        shadowElevation = 8.dp
    ) {
        Image(
            painter = painterResource(imageRes),
            contentDescription = null,
            modifier = Modifier
                .padding(24.dp)
                .size(300.dp),
            contentScale = ContentScale.Crop
        )
    }
}


@Composable
fun ArtworkDescriptor(@StringRes titleRes: Int,modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFFECEBF4))
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(titleRes),
            fontSize = 26.sp,
            fontWeight = FontWeight.Light
        )
        Row{
            Text(
                text = stringResource(R.string.artist),
                fontWeight = FontWeight.Bold
            )
            Text(text = " (${stringResource(R.string.year)})")
        }
    }
}

@Composable
fun DisplayController(
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            onClick = onPreviousClick,
            modifier = Modifier.width(140.dp)
        ) {
            Text(stringResource(R.string.previous))
        }

        Button(
            onClick = onNextClick,
            modifier = Modifier.width(140.dp)
        ) {
            Text(stringResource(R.string.next))
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ArtSpacePreview() {
    ArtSpaceTheme {
        ArtSpaceScreen()
    }
}