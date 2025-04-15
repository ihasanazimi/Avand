package ir.ha.goodfeeling.screens

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import ir.ha.goodfeeling.R
import ir.ha.goodfeeling.ui.theme.CustomTypography
import ir.ha.goodfeeling.ui.theme.GoodFeelingTheme
import kotlin.math.roundToInt


@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewScreen(
    url: String,
    onBackPressed: (() -> Unit)? = null
) {

    val systemUiController = rememberSystemUiController()
    var useDarkIcons = isSystemInDarkTheme()
    var defaultColor = MaterialTheme.colorScheme.primary

    val context = LocalContext.current
    val loading = remember { mutableStateOf(true) }

    val webView = remember {
        WebView(context).apply {
            settings.javaScriptEnabled = true
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            webViewClient = object : WebViewClient() {
                override fun onPageStarted(
                    view: WebView?,
                    url: String?,
                    favicon: android.graphics.Bitmap?
                ) {
                    loading.value = true
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    loading.value = false
                }
            }
            loadUrl(url)
        }
    }

    BackHandler(enabled = true) {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            onBackPressed?.invoke()
        }
    }

    Column(
        modifier = Modifier
            .padding(WindowInsets.statusBars.asPaddingValues())
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(defaultColor)
        ) {

            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "close btn",
                tint = MaterialTheme.colorScheme.background,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .clip(
                        CircleShape
                    )
                    .clickable {
                        onBackPressed?.invoke()
                    }
                    .size(28.dp)
                    .align(Alignment.CenterVertically)
            )

            Box(Modifier.fillMaxWidth().fillMaxHeight() , contentAlignment = Alignment.CenterStart){
                Text(
                    modifier = Modifier
                        .fillMaxWidth().padding(end = 16.dp),
                    text = url.take((url.length / 1.5f).roundToInt()),
                    style = CustomTypography.bodyLarge.copy(color = Color.White , textAlign = TextAlign.Center),
                    maxLines = 1,
                )
            }

        }

        Box(modifier = Modifier.fillMaxSize()) {
            AndroidView(factory = { webView }, modifier = Modifier.fillMaxSize())
            if (loading.value) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.4f)),
                    contentAlignment = Alignment.Center
                ) {
                    val composition by rememberLottieComposition(
                        LottieCompositionSpec.RawRes(R.raw.loading)
                    )
                    val progress by animateLottieCompositionAsState(
                        composition,
                        iterations = LottieConstants.IterateForever
                    )

                    LottieAnimation(
                        composition = composition,
                        progress = { progress },
                        modifier = Modifier.size(120.dp)
                    )
                }
            }
        }
    }

    useDarkIcons = isSystemInDarkTheme()
    SetStatusBarColor(MaterialTheme.colorScheme.primary, useDarkIcons)

    DisposableEffect(Unit) {
        onDispose {
            defaultColor = Color.Transparent
            systemUiController.setStatusBarColor(defaultColor,useDarkIcons.not())
        }
    }

}


@Composable
fun SetStatusBarColor(color: Color, darkIcons: Boolean = true) {
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(
            color = color,
            darkIcons = darkIcons
        )
    }
}


@Preview
@Composable
private fun WebViewScreenPreview() {
    GoodFeelingTheme {
        WebViewScreen(
            url = "https://www.google.com",
            onBackPressed = {

            }
        )
    }
}