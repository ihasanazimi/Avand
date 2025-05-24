package ir.hasanazimi.avand.presentation.screens.web_view

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
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import ir.hasanazimi.avand.R
import ir.hasanazimi.avand.presentation.theme.CustomTypography


@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewScreen(
    newsUrl: String,
    onBackPressed: (() -> Unit)? = null
) {

    if (newsUrl.isNotEmpty()) {

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
                    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                        loading.value = true
                    }

                    override fun onPageFinished(view: WebView?, url: String?) {
                        loading.value = false
                    }
                }
            }
        }

        LaunchedEffect(newsUrl) {
            webView.loadUrl(newsUrl)
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
                            .background(Color.Black.copy(alpha = 0.8f)).clickable{}.focusable(true),
                        verticalArrangement = Arrangement.Center,
                    ) {

                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(R.drawable.loading),
                                contentDescription = "loading image",
                                modifier = Modifier
                                    .size(32.dp)
                                    .graphicsLayer {
                                        rotationZ = rotation
                                    },
                                colorFilter = ColorFilter.tint(Color.White)
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


            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(MaterialTheme.colorScheme.onSecondary)
            )


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.08f)
            ) {
                Button(
                    modifier = Modifier.fillMaxSize(),
                    shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp), onClick = {
                        onBackPressed?.invoke()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                ) {
                    Text(
                        modifier = Modifier,
                        text = "بستن",
                        style = CustomTypography.bodyLarge.copy(
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSecondary
                        ),
                        maxLines = 1,
                    )
                }
            }
        }
    }
}