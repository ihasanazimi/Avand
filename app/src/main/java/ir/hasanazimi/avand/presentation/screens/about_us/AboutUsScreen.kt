package ir.hasanazimi.avand.presentation.screens.about_us

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ir.hasanazimi.avand.presentation.theme.AvandTheme
import ir.hasanazimi.avand.R
import ir.hasanazimi.avand.presentation.theme.CustomTypography


@Composable
fun AboutUsScreen(
    navHostController: NavHostController,
    onGithubClick: () -> Unit = {},
    onLinkedinClick: () -> Unit = {},
    onMessageClick: () -> Unit = {},
) {
    AvandTheme {

        val backgroundColor = Brush.verticalGradient(
            colors = listOf(MaterialTheme.colorScheme.primary, Color.Black)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
                .padding(start = 16.dp, end = 16.dp, top = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "درباره توسعه دهنده",
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                style = CustomTypography.bodyLarge,
                color = Color.White,
            )

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(8.dp),
                colors = CardDefaults.cardColors()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceAround,
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Card(colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary)) {
                            Icon(
                                painter = painterResource(id = R.drawable.linkedin),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(37.dp)
                                    .padding(6.dp)
                                    .clickable {
                                        onLinkedinClick.invoke()
                                    }, tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }


                        Box(
                            modifier = Modifier
                                .size(130.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(Color.LightGray),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.me),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        }


                        Card(colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary)) {
                            Icon(
                                painter = painterResource(id = R.drawable.github),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(37.dp)
                                    .padding(7.dp)
                                    .clickable {
                                        onGithubClick.invoke()
                                    }, tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        "سلام!\n" +
                                "من حسن عظیمی\u200Cام، یه توسعه\u200Cدهنده\u200Cی اندروید که چند سالی هست تو دنیای برنامه\u200Cنویسی موبایل فعالیت می\u200Cکنم. تو این مدت روی اپلیکیشن\u200Cهای مختلفی کار کردم، از پروژه\u200Cهای سازمانی گرفته تا خدمات شهری و پلتفرم\u200Cهای مالی.\n" +
                                "\n" +
                                "همیشه سعی کردم اپ\u200Cهایی بسازم که فقط یه ابزار نباشن، بلکه یه تجربه\u200Cی خوب و راحت برای کاربرها باشن. یادگیری برام تموم\u200Cشدنی نیست و همیشه دنبال بهتر شدنم.\n" +
                                "\n" +
                                "مرسی که بهم و اپلیکیشنم اعتماد کردید :)\n" +
                                "با آرزوی بهترین\u200Cها،\n" +
                                "حسن عظیمی",
                        style = CustomTypography.labelSmall,
                        lineHeight = TextUnit(
                            22f,
                            TextUnitType.Sp
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                }

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 8.dp)
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(),
                    onClick = onMessageClick,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        "ارتباط با توسعه دهنده",
                        style = CustomTypography.bodyLarge.copy(color = MaterialTheme.colorScheme.onPrimary)
                    )
                }
            }

            TextButton(
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                onClick = {
                    navHostController.popBackStack()
                },
            ) {
                Text("بـازگـشـت", color = Color.White)
            }
        }
    }
}


@Preview
@Composable
private fun AboutUsScreenPreview() {
    AboutUsScreen(rememberNavController())
}