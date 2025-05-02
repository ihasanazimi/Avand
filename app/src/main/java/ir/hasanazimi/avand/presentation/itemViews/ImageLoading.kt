package ir.hasanazimi.avand.presentation.itemViews

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ir.hasanazimi.avand.R

@Composable
fun ImageLoading(modifier: Modifier = Modifier , url : String) {
    Log.i("TAG", "ImageLoading: $url ")
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .crossfade(true)
            .addHeader("User-Agent", "Mozilla/5.0 (Android; Mobile; rv:68.0)")
            .addHeader("Accept", "image/*")
            .listener(
                onStart = {
                    Log.d("Coil", "Loading started for $url")
                },
                onError = { _, error ->
                    Log.e("CoilError", "Failed to load ${url}: ${error.throwable.message}")
                },
                onSuccess = { _, _ ->
                    Log.d("CoilSuccess", "Successfully loaded $url")
                }
            )
            .build(),
        contentDescription = url,
        contentScale = if (url.isEmpty()) ContentScale.Inside else ContentScale.Crop,
        modifier = if (url.isEmpty()) Modifier.fillMaxWidth().height(0.dp) else modifier,
        placeholder = painterResource(R.drawable.place_holder2),
        error = painterResource(R.drawable.no_image)
    )
}