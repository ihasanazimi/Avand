package ir.hasanazimi.avand.presentation.screens

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
import ir.hasanazimi.avand.R
import ir.hasanazimi.avand.presentation.theme.AvandTheme
import ir.hasanazimi.avand.presentation.theme.CustomTypography


@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewScreen(
    url: String,
    isShow: Boolean,
    onBackPressed: (() -> Unit)? = null
) {

    if (isShow && url.isNotEmpty()) {

        val systemUiController = rememberSystemUiController()
        var useDarkIcons = isSystemInDarkTheme()
        var secondaryColor = MaterialTheme.colorScheme.secondary

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
                        favicon: Bitmap?
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
                .fillMaxSize()
                .padding(WindowInsets.statusBars.asPaddingValues())
        ) {

            Box(modifier = Modifier.weight(0.9f)) {
                AndroidView(factory = { webView }, modifier = Modifier.fillMaxSize())
                if (loading.value) {
                    val infiniteTransition = rememberInfiniteTransition(label = "rotation")
                    val rotation by infiniteTransition.animateFloat(
                        initialValue = 0f,
                        targetValue = 360f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(durationMillis = 1200, easing = LinearEasing),
                            repeatMode = RepeatMode.Restart
                        ),
                        label = "rotationAnim"
                    )

                    Column(
                        Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.9f)),
                        verticalArrangement = Arrangement.Center,
                    ) {

                        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                            Image(
                                painter = painterResource(R.drawable.loading),
                                contentDescription = "loading image",
                                modifier = Modifier
                                    .size(32.dp)
                                    .graphicsLayer {
                                        rotationZ = rotation
                                    }
                            )
                        }

                        Text(
                            text = "کمی صبر کنید..",
                            textAlign = TextAlign.Center,
                            style = CustomTypography.labelLarge.copy(color = Color.White),
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(vertical = 8.dp)
                        )
                    }
                }
            }


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.08f)
            ) {
                Button(
                    modifier = Modifier.fillMaxSize(),
                    shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp), onClick = {
                        onBackPressed?.invoke()
                    }
                ) {
                    Text(
                        modifier = Modifier,
                        text = "بستن",
                        style = CustomTypography.bodyLarge.copy(
                            textAlign = TextAlign.Center
                        ),
                        maxLines = 1,
                    )
                }
            }
        }
    }
}


/*useDarkIcons = isSystemInDarkTheme()
 SetStatusBarColor(defaultColor, useDarkIcons)

 DisposableEffect(Unit) {
     onDispose {
         defaultColor = Color.Transparent
         systemUiController.setStatusBarColor(defaultColor,useDarkIcons.not())
     }
 }*/

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
    AvandTheme {
        WebViewScreen(
            url = "https://www.google.com",
            isShow = false,
            onBackPressed = {}
        )
    }
}