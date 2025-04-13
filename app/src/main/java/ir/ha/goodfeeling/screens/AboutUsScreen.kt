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

                        Card(modifier = Modifier.size(38.dp), shape = RoundedCornerShape(4.dp)) {
                            Image(
                                painter = painterResource(id = R.drawable.linkedin),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clickable {
                                        onLinkedinClick.invoke()
                                    }
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

                        Card(modifier = Modifier.size(42.dp), shape = RoundedCornerShape(4.dp)) {
                            Image(
                                painter = painterResource(id = R.drawable.github),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clickable {
                                        onGithubClick.invoke()
                                    }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        "سلام! من حسن عظیمی هستم، توسعه\u200Cدهنده\u200Cی اندروید." +
                                "\n" +
                                "چند سالی است که در حوزه\u200Cی برنامه\u200Cنویسی موبایل فعالیت می\u200Cکنم و با تمرکز بر ایجاد اپلیکیشن\u200Cهایی کاربرمحور، تجربه\u200Cهای متنوعی در پروژه\u200Cهای مختلف کسب کرده\u200Cام. همکاری با تیم\u200Cها و شرکت\u200Cهای بزرگ در توسعه\u200Cی اپلیکیشن\u200Cهای سازمانی، خدمات شهری و پلتفرم\u200Cهای مالی، دیدگاه من را نسبت به طراحی و توسعه\u200Cی نرم\u200Cافزار عمیق\u200Cتر کرده است.\u200B\n" +
                                "\n" +
                                "همواره در تلاش هستم تا با یادگیری مستمر، اپلیکیشن\u200Cهایی بسازم که فراتر از یک ابزار، تجربه\u200Cای مثبت و کاربردی برای کاربران فراهم کنند.\u200B\n" +
                                "\n" +
                                "از اعتماد شما به من و اپلیکیشنم سپاسگزارم.\u200B\n" +
                                "\n" +
                                "با احترام،\n" +
                                "حسن عظیمی",
                        style = CustomTypography.labelSmall,
                        lineHeight = TextUnit(
                            22f,
                            TextUnitType.Sp
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Button(
                            modifier = modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            onClick = onMessageClick,
                            colors = ButtonDefaults.buttonColors(containerColor = LightPrimary)
                        ) {
                            Text("ارتباط با توسعه دهنده" , style = CustomTypography.bodyLarge.copy(color = Color.White))
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