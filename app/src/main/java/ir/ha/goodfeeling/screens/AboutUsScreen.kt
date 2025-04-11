package ir.ha.goodfeeling.screens

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
import ir.ha.goodfeeling.R
import ir.ha.goodfeeling.ui.theme.CustomTypography
import ir.ha.goodfeeling.ui.theme.DarkBackground
import ir.ha.goodfeeling.ui.theme.GoodFeelingTheme
import ir.ha.goodfeeling.ui.theme.LightPrimary


@Composable
fun AboutUsScreen(
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
    onGithubClick: () -> Unit = {},
    onLinkedinClick: () -> Unit = {},
    onMessageClick: () -> Unit = {},
) {
    GoodFeelingTheme {

        val backgroundColor = Brush.verticalGradient(
            colors = listOf(LightPrimary, Color.Black)
        )

        Column(
            modifier = modifier
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
                style = CustomTypography.titleLarge,
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
                        Image(
                            painter = painterResource(id = R.drawable.linkedin),
                            contentDescription = null,
                            modifier = Modifier
                                .size(32.dp)
                                .clickable {
                                    onLinkedinClick.invoke()
                                }
                        )

                        Box(
                            modifier = Modifier
                                .size(100.dp)
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

                        Image(
                            painter = painterResource(id = R.drawable.github),
                            contentDescription = null,
                            modifier = Modifier
                                .size(35.dp)
                                .clickable {
                                    onGithubClick.invoke()
                                }
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        "سلام! من حسن عظیمی هستم، توسعه\u200Cدهنده\u200Cی اندروید. چند سالی می\u200Cشه که وارد دنیای برنامه\u200Cنویسی موبایل شدم و از همون روز اول، با علاقه و پشتکار سعی کردم اپلیکیشن\u200Cهایی بسازم که علاوه بر کارایی، تجربه\u200Cی خوبی برای کاربرها فراهم کنن.\n" +
                                "\n" +
                                "در این مسیر، افتخار همکاری با شرکت\u200Cها و تیم\u200Cهای حرفه\u200Cای زیادی رو داشتم. تجربه\u200Cهایی مثل مشارکت در توسعه\u200Cی اپلیکیشن\u200Cهای بزرگ سازمانی، سیستم\u200Cهای خدمات شهری، پلتفرم\u200Cهای مالی و پروژه\u200Cهایی مثل کیف پول دیجیتال، سامانه\u200Cهای سلامت و اپلیکیشن\u200Cهای خدماتی، کمکم کرده نگاه عمیق\u200Cتری به توسعه و طراحی نرم\u200Cافزار داشته باشم.\n" +
                                "\n" +
                                "برام مهمه که اپلیکیشنی که می\u200Cسازم، فقط یه ابزار نباشه؛ بلکه تجربه\u200Cای مثبت و کاربردی برای مخاطب ایجاد کنه.\n" +
                                "همچنان در حال یادگیری\u200Cام و به این مسیر علاقه\u200Cمندم — چون هر پروژه\u200Cی جدید، فرصتیه برای بهتر شدن.\n" +
                                "\n" +
                                "ممنونم که به من و اپلیکیشنم اعتماد کردی \uD83C\uDF31\n" +
                                "با احترام،\n" +
                                "حسن عظیمی",
                        style = CustomTypography.labelSmall,
                        lineHeight = TextUnit(
                            20f,
                            TextUnitType.Sp
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Button(
                            modifier = modifier.fillMaxWidth(),
                            onClick = onMessageClick,
                            colors = ButtonDefaults.buttonColors(containerColor = LightPrimary)
                        ) {
                            Text("ارتباط با توسعه دهنده")
                        }

                        TextButton(
                            modifier = modifier.fillMaxWidth(),
                            onClick = {
                                navHostController.popBackStack()
                            },
                        ) {
                            Text("بازگشت")
                        }
                    }
                }
            }
        }
    }
}


@Preview
@Composable
private fun AboutUsScreenPreview() {
    AboutUsScreen(rememberNavController())
}