package ir.ha.goodfeeling.screens

import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import ir.ha.goodfeeling.ui.theme.GoodFeelingTheme

@Composable
fun WebViewScreen(
    url: String,
    modifier: Modifier = Modifier,
    onBackPressed: (() -> Unit)? = null
) {
    val context = LocalContext.current
    val webView = remember {
        WebView(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            webViewClient = WebViewClient()
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

    AndroidView(
        factory = { webView },
        modifier = modifier.fillMaxSize()
    )
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